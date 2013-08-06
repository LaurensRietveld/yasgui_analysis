package org.data2semantics.yasgui.analysis.loaders;

import java.io.IOException;
import org.data2semantics.yasgui.analysis.Collection;
import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.filters.Filter;


public abstract class Loader {
	public static enum InputType {
		OLD_QUERIES("input/old_queries.csv", -1, 0, 1 ),//column IDs (-1 if column id for this data does not exist)
		NEW_QUERIES("input/new_logs.csv", 0, 1, 2),
		OLD_ENDPOINTS("input/old_endpoints.csv", 0, -1, 1);
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
	protected Collection collection = new Collection();
	protected InputType[] inputTypes;
	protected int invalidQueries = 0;
	protected int validQueries = 0;
	protected Filter[] filters;
	public Loader(InputType... inputType) {
		this.inputTypes = inputType;
	}
	
	public void setFilters(Filter... filters) {
		this.filters = filters;
	}
	public abstract void load(Loader.InputType inputType) throws IOException;
	public abstract void load() throws IOException;
	public Collection getCollection() {
		return this.collection;
	}
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
	
	protected boolean validColumn(String[] line, int column) {
		return (column >= 0 && column < line.length);
	}
}
