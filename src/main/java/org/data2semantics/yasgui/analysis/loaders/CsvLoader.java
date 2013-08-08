package org.data2semantics.yasgui.analysis.loaders;

import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.validator.routines.UrlValidator;
import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.filters.SimpleBgpFilter;


import au.com.bytecode.opencsv.CSVReader;


public class CsvLoader extends Loader {
	private UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES + UrlValidator.ALLOW_2_SLASHES + UrlValidator.ALLOW_LOCAL_URLS) {
		private static final long serialVersionUID = -7572321851960984161L;
		/** allow missing scheme. */
        @Override
        public boolean isValid(String value) {
            return super.isValid(value) || value.contains("query") || value.contains("sparql");
        }
	};
	public CsvLoader(Loader.InputType... inputTypes) {
		super(inputTypes);
	}

	
	public void load(Loader.InputType inputType) throws IOException {
		CSVReader csvReader = new CSVReader(new FileReader(inputType.getInputPath()), ','); 
		String[] line;
		while ((line = csvReader.readNext()) != null) {
			
			String endpoint = null;
			if (validColumn(line, inputType.getEndpointCol())) {
				if (urlValidator.isValid(line[inputType.getEndpointCol()])) {
					endpoint = line[inputType.getEndpointCol()];
					if (inputType.getCountCol() >= 0) {
						collection.getEndpointCollection().addEndpoint(endpoint, Integer.parseInt(line[inputType.getCountCol()].replace(",","")));
					} else {
						collection.getEndpointCollection().addEndpoint(endpoint);
					}
					
				}
			}
			if (validColumn(line, inputType.getQueryCol())) {
				Query query = getParsedAndFilteredQuery(line[inputType.getQueryCol()]);
				
				if (query != null) {
					query.setCount(Integer.parseInt(line[inputType.getCountCol()]));
					if (endpoint != null) query.setEndpoints(endpoint);
					collection.getQueryCollection().addQuery(query);
				}
			}
		}
		csvReader.close();
	}
	
	

	
	
	public static void main(String[] args) throws IOException {
		CsvLoader loader = new CsvLoader(Loader.InputType.OLD_ENDPOINTS, Loader.InputType.OLD_QUERIES, Loader.InputType.NEW_QUERIES);
		loader.setFilters(new SimpleBgpFilter());
		loader.load();
		System.out.println(loader.getCollection().getQueryCollection().toString());
		System.out.println(loader.getCollection().getEndpointCollection().toString());
		
		
	}
}
