package org.data2semantics.yasgui.analysis;

import java.util.HashMap;

public class EndpointCollection {
	
	private HashMap<String, Integer> endpoints = new HashMap<String, Integer>();
	
	public EndpointCollection() {
		
	}
	
	
	public void addEndpoint(String endpoint) {
		addEndpoint(endpoint, 1);
	}
	
	public void addEndpoint(String endpoint, int count) {
		if (endpoints.containsKey(endpoint)) count += endpoints.get(endpoint).intValue();
		endpoints.put(endpoint, count);
	}
	
	public HashMap<String, Integer> getEndpoints() {
		return this.endpoints;
	}
	
	public int getTotalCount() {
		int count = 0;
		for (Integer endpointCount: endpoints.values()) {
			count += endpointCount.intValue();
		}
		return count;
	}
	
	public int getEndpointCount(String endpoint) {
		int count = 0;
		if (this.endpoints.containsKey(endpoint)) {
			count = endpoints.get(endpoint).intValue();
		}
		return count;
	}
	
	
	public String toString() {
		return "total endpoint count: " + getTotalCount() + " distinct endpoint count: " + getEndpoints().size();
	}
	
	public static void main(String[] args) {
	}
}
