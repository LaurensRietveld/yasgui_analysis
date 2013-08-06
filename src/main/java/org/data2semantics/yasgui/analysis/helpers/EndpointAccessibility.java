package org.data2semantics.yasgui.analysis.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.QueryCollection;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import au.com.bytecode.opencsv.CSVReader;

public class EndpointAccessibility extends AnalysisHelper {
	
	
	
	
	//get list from ckan
	
	//check whether we can access the endpoints
	
	
	
	ArrayList<String> mondecaEndpoints = new ArrayList<String>();
	public EndpointAccessibility(QueryCollection queryCollection) {
		super(queryCollection);
	}

	public void calc() throws IOException, ParseException {
		queryCkan();
		for (Query query: queryCollection.getQueries()) {
			
		}
		
	}
	
	private ResultSet queryCkan() {
		com.hp.hpl.jena.query.Query query = QueryFactory.create(getCkanQuery());
		QueryEngineHTTP queryExecution = new QueryEngineHTTP("http://semantic.ckan.net/sparql", query);
		
		return queryExecution.execSelect();
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
	
	
	public static void main(String[] args) {
	}
}
