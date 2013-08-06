package org.data2semantics.yasgui.analysis;

import java.util.HashMap;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

public class Query extends com.hp.hpl.jena.query.Query {
	
	private HashMap<String, String> endpoints = new HashMap<String, String>();
	private int count;


	public String[] getEndpoints() {
		return endpoints.values().toArray (new String[endpoints.values().size ()]);
	}
	

	public void setEndpoints(String... endpoints) {
		if (endpoints != null) {
			for (String endpoint: endpoints) {
				if (endpoint != null) {
					this.endpoints.put(endpoint, endpoint);
				}
			}
		}
	}
	
	


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	public static Query create(String queryString) {
		Query query = new Query();
		return (Query)(QueryFactory.parse(query, queryString, null, Syntax.defaultQuerySyntax));
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Query query = Query.create("SELECT * WHERE {?x ?g ?jh}");
	}


	public void appendQueryObject(Query query) {
		setEndpoints(query.getEndpoints());
		this.count += query.getCount();
	}
}
