package org.data2semantics.yasgui.analysis.loaders;

import java.io.IOException;

import org.data2semantics.yasgui.analysis.Collection;
import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.filters.Filter;

import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.sparql.expr.ExprException;


public abstract class Loader {
	public static enum InputType {
		OLD_QUERIES("input/old_queries.csv", null, -1, 0, 1 ),//column IDs (-1 if column id for this data does not exist)
		NEW_QUERIES("input/new_logs.csv", null, 0, 1, 2),
		OLD_ENDPOINTS("input/old_endpoints.csv", null, 0, -1, 1),
		DBP_QUERIES("input/dbp.log", "cache/dbp.cache", -1, -1, -1);
		
		private int endpointCol = 0;
		private int queryCol = 0;
		private int countCol = 0;
		private String inputPath = null;
		private String cachePath = null;
	    private InputType(String inputPath, String cachePath, int endpointCol, int queryCol, int countCol) {
	        this.endpointCol = endpointCol;
	        this.queryCol = queryCol;
	        this.countCol = countCol;
	        this.cachePath = cachePath;
	        this.inputPath = inputPath;
	    }
	    public int getQueryCol() {return queryCol;}
	    public int getEndpointCol() {return endpointCol;}
	    public int getCountCol() {return countCol;}
	    public String getInputPath() {return inputPath;}
	    public String getCachePath() {return cachePath;}
	};
	protected Collection collection = new Collection();
	protected InputType[] inputTypes;
	protected int invalidQueries = 0;
	protected int validQueries = 0;
	protected int filteredQueries = 0;
	protected Filter[] filters;
	public Loader(InputType... inputType) {
		this.inputTypes = inputType;
	}
	
	public void setFilters(Filter... filters) {
		this.filters = filters;
	}
	public abstract void load(Loader.InputType inputType) throws IOException;
	public void load() throws IOException {
		for (Loader.InputType inputType: inputTypes) {
			load(inputType);
		}
	}
	public Collection getCollection() {
		return this.collection;
	}
	protected boolean checkFilters(Query query, Filter... filters) {
		boolean passed = true;
		try {
			for (Filter filter : filters) {
				if (filter.filter(query)) {
					passed = false;
					filteredQueries++;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(query.toString());
			e.printStackTrace();
			System.exit(1);
		}
		return passed;
	}
	
	protected Query getParsedAndFilteredQuery(String queryString) {
		Query query = null;
		try {
			query = Query.create(queryString);
			validQueries++;
		} catch (QueryParseException e){
			//unable to parse query. invalid!
			invalidQueries++;
		} catch (ExprException e){
			//unable to parse regex in query. invalid!
			invalidQueries++;
		} catch (Exception e) {
			System.out.println("failed to parse query string: " + queryString);
			e.printStackTrace();
			System.exit(1);
		}
		if (!checkFilters(query, filters)) query = null;
		return query;
	}
	
	protected boolean validColumn(String[] line, int column) {
		return (column >= 0 && column < line.length);
	}
	public String toString() {
		return "valids: " + validQueries + " invalids: " + invalidQueries;
	}
}
