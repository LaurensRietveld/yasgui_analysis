package org.data2semantics.yasgui.analysis.loaders;

import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.math.NumberUtils;
import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.QueryCollection;
import org.data2semantics.yasgui.analysis.filters.Filter;
import org.data2semantics.yasgui.analysis.filters.SimpleBgpFilter;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.sparql.expr.ExprException;
import au.com.bytecode.opencsv.CSVReader;


public class CsvLoader extends Loader {
	
	public CsvLoader(Loader.InputType inputType) {
		super(inputType);
	}

	
	public QueryCollection loadQueries(Filter... filters) throws IOException {
		QueryCollection collection = new QueryCollection();
		CSVReader csvReader = new CSVReader(new FileReader(inputFile), ','); 
		String[] line;
		while ((line = csvReader.readNext()) != null) {
			if (line.length < NumberUtils.max(inputType.getCountCol(), inputType.getEndpointCol(), inputType.getQueryCol())) continue;
			
			String endpoint = null;
			if (inputType.getEndpointCol() >= 0) {
				endpoint = line[inputType.getEndpointCol()];
				if (!endpoint.startsWith("http://")) {
					//based on our input type we can expect this endpoint. it its not there, skip this line in the csv.
					continue;
				}
			}
			Query query;
			try {
				query = Query.create(line[inputType.getQueryCol()]);
				validQueries++;
			} catch (QueryParseException e){
				//unable to parse query. invalid!
				invalidQueries++;
				continue;
			} catch (ExprException e){
				//unable to parse regex in query. invalid!
				invalidQueries++;
				continue;
			}
			
			if (checkFilters(query, filters)) {
				query.setCount(Integer.parseInt(line[inputType.getCountCol()]));
				query.setEndpoint(endpoint);
				collection.addQuery(query);
			}
		}
		csvReader.close();
		return collection;
	}
	
	
	
	public String toString() {
		return "valids: " + validQueries + " invalids: " + invalidQueries;
	}
	
	
	public static void main(String[] args) throws IOException {
		CsvLoader loader = new CsvLoader(Loader.InputType.OLD_QUERIES);
		QueryCollection collection = loader.loadQueries(new SimpleBgpFilter());
		System.out.println(loader.toString());
		System.out.println(collection.toString());
		
		
	}
}
