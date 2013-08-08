package org.data2semantics.yasgui.analysis.helpers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import org.data2semantics.yasgui.analysis.Collection;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class CkanStats extends AnalysisHelper {
	
	private HashMap<String, Integer> ckanEndpoints;
	private int totalCkanEndpointsCount = 0;
	
	
	ArrayList<String> mondecaEndpoints = new ArrayList<String>();
	public CkanStats(Collection collection) {
		super(collection);
	}

	public void calc() throws IOException, ParseException {
		getCkanStats();
		System.out.println(toString());
		
	}
	
	private void getCkanStats() {
		ckanEndpoints = getCkanEndpoints();
		for (Integer count: ckanEndpoints.values()) {
			totalCkanEndpointsCount += count.intValue();
		}
	}
	
	/**
	 * get list of endpoints from ckan, which are in our endpoint selection as well.
	 * @return
	 */
	private HashMap<String, Integer> getCkanEndpoints() {
		com.hp.hpl.jena.query.Query query = QueryFactory.create(getCkanQuery());
		QueryEngineHTTP queryExecution = new QueryEngineHTTP("http://semantic.ckan.net/sparql", query);
		HashMap<String, Integer> endpoints = new HashMap<String, Integer>();
		ResultSet resultSet = queryExecution.execSelect();
		while (resultSet.hasNext()) {
			String endpoint = resultSet.next().get("endpoint").toString();
			if (collection.getEndpointCollection().getEndpoints().containsKey(endpoint)) {
				endpoints.put(endpoint, collection.getEndpointCollection().getEndpointCount(endpoint));
			}
		}
		return endpoints;
	}
	private String getCkanQuery() {
		return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" 
				+ "		PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "		PREFIX dcat: <http://www.w3.org/ns/dcat#>\n" 
				+ "		PREFIX dcterms: <http://purl.org/dc/terms/>\n" + "\n"
				+ "		SELECT DISTINCT ?endpoint  {\n"
				+ "		  ?dataset dcat:distribution ?distribution.\n"
				+ "		?distribution dcterms:format ?format.\n"
				+ "		?format rdf:value 'api/sparql'.\n"
				+ "		?distribution dcat:accessURL ?endpoint.\n"
				+ "		}";
	}
	
	public String toString() {
		return "#distinct ckan endpoints: " + ckanEndpoints.size() 
				+ " total ckan endpoints" + totalCkanEndpointsCount 
				+ " ";
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		CkanStats ea = new CkanStats(new Collection());
		ea.calc();
	}
}
