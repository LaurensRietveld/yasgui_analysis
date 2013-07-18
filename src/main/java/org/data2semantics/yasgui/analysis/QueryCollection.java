package org.data2semantics.yasgui.analysis;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryCollection {
	
	private ArrayList<Query> queries = new ArrayList<Query>();
	
	private HashMap<String, Object> endpoints = new HashMap<String, Object>();
	private HashMap<String, String> distinctQueries = new HashMap<String, String>();
	int totalQueryCount = 0;
	public QueryCollection() {
		
	}
	
	
	public void addQuery(Query query) {
		queries.add(query);
		distinctQueries.put(query.toString(), null);
		totalQueryCount += query.getCount();
		endpoints.put(query.getEndpoint(), null);
		
	}
	
	
	
	public int getTotalQueryCount() {
		return this.totalQueryCount;
	}
	
	public int getDistinctQueryCount() {
		return distinctQueries.size();
	}
	
	public int getDistinctEndpoints() {
		return endpoints.size();
	}
	public String toString() {
		return "totalCount: " + getTotalQueryCount() + " distinct query count: " + getDistinctQueryCount() + " distinct endpoints: " + getDistinctEndpoints();
	}
	
	public static void main(String[] args) {
	}
}
