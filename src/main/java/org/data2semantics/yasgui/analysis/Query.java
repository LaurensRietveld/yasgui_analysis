package org.data2semantics.yasgui.analysis;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

public class Query extends com.hp.hpl.jena.query.Query {
	
	private String endpoint;
	private int count;


	public String getEndpoint() {
		return endpoint;
	}


	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
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
}
