package org.data2semantics.yasgui.analysis;


public class Collection {
	
	private QueryCollection queryCollection = new QueryCollection();
	private EndpointCollection endpointCollection = new EndpointCollection();
	public Collection() {
	}
	
	
	public QueryCollection getQueryCollection() {
		return queryCollection;
	}


	public void setQueryCollection(QueryCollection queryCollection) {
		this.queryCollection = queryCollection;
	}


	public EndpointCollection getEndpointCollection() {
		return endpointCollection;
	}


	public void setEndpointCollection(EndpointCollection endpointCollection) {
		this.endpointCollection = endpointCollection;
	}
}
