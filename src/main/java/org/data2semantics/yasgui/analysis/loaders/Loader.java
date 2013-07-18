package org.data2semantics.yasgui.analysis.loaders;

import java.io.File;
import java.io.IOException;

import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.QueryCollection;
import org.data2semantics.yasgui.analysis.filters.Filter;


public abstract class Loader {
	public static enum InputType {
		OLD_QUERIES("input/old_queries.csv", -1, 0, 1 ),
		NEW_QUERIES("input/new_logs.csv", 0, 1, 2);
		private int endpointCol = 0;
		private int queryCol = 0;
		private int countCol = 0;
		private String path = null;
	    private InputType(String path, int endpointCol, int queryCol, int countCol) {
	        this.endpointCol = endpointCol;
	        this.queryCol = queryCol;
	        this.countCol = countCol;
	        this.path = path;
	    }
	    public int getQueryCol() {return queryCol;}
	    public int getEndpointCol() {return endpointCol;}
	    public int getCountCol() {return countCol;}
	    public String getPath() {return path;}
	};
	
	protected File inputFile;
	protected InputType inputType;
	protected int invalidQueries = 0;
	protected int validQueries = 0;
	
	public Loader(InputType inputType) {
		this.inputType = inputType;
		this.inputFile = new File(inputType.getPath());
	}
	
	public abstract QueryCollection loadQueries(Filter... filters) throws IOException;
	
	
	protected boolean checkFilters(Query query, Filter... filters) {
		boolean passed = true;
		try {
			for (Filter filter : filters) {
				if (filter.filter(query)) {
					passed = false;
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
}
