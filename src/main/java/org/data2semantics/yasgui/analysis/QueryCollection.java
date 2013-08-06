package org.data2semantics.yasgui.analysis;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryCollection {
	
	private ArrayList<Query> queries = new ArrayList<Query>();
	
	private HashMap<String, Object> endpoints = new HashMap<String, Object>();
	private HashMap<String, Query> distinctQueries = new HashMap<String, Query>();
	int totalQueryCount = 0;
	public QueryCollection() {
		
	}
	
	
	public void addQuery(Query query) {
		queries.add(query);
		if (distinctQueries.containsKey(query.toString())) {
			distinctQueries.get(query.toString()).appendQueryObject(query);
		} else {
			distinctQueries.put(query.toString(), query);
			
		}
		totalQueryCount += query.getCount();
		for (String endpoint: query.getEndpoints()) {
			endpoints.put(endpoint, endpoint);
		}
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
	
	public ArrayList<Query> getQueries() {
		return this.queries;
	}
	
	public String toString() {
		return "totalCount query: " + getTotalQueryCount() + " distinct query count: " + getDistinctQueryCount() + " distinct query endpoints: " + getDistinctEndpoints();
	}
	
	public static void main(String[] args) {
	}
}
