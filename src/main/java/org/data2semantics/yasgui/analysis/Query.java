package org.data2semantics.yasgui.analysis;

import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.syntax.ElementWalker;

public class Query extends com.hp.hpl.jena.query.Query {
	
	private HashMap<String, String> endpoints = new HashMap<String, String>();
	
	private int numberOfNonOptionalTriplePatterns = 0;
	private int triplePatternCountCcv = 0;
	private int triplePatternCountCvv = 0;
	private int triplePatternCountVcc = 0;
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
		query = (Query)(QueryFactory.parse(query, queryString, null, Syntax.defaultQuerySyntax));
		query.generateQueryStats();
		return query;
	}



	public void appendQueryObject(Query query) {
		setEndpoints(query.getEndpoints());
		this.count += query.getCount();
	}
	
	public void generateQueryStats() {
		getTriplePatternsInfo();
		getJoinsInfo();
	}
	
	private void getTriplePatternsInfo() {
		Element qPattern = getQueryPattern();

		// This will walk through all parts of the query
		ElementWalker.walk(qPattern,
		    // For each element...
		    new ElementVisitorBase() {
		        // ...when it's a block of triples...
		        public void visit(ElementPathBlock el) {
		        	
		            // ...go through all the triples...
		            Iterator<TriplePath> triples = el.patternElts();
		            while (triples.hasNext()) {
		            	numberOfNonOptionalTriplePatterns++;
		            	TriplePath triplePath = triples.next();
		            	if (	!triplePath.getSubject().isVariable() && 
		            			!triplePath.getPredicate().isVariable() &&
		            			triplePath.getObject().isVariable()) {
		            		triplePatternCountCcv++;
		            	} else if (	!triplePath.getSubject().isVariable() && 
			            			triplePath.getPredicate().isVariable() &&
			            			triplePath.getObject().isVariable()) {
		            		triplePatternCountCvv++;
		            	} else if (	triplePath.getSubject().isVariable() && 
			            			!triplePath.getPredicate().isVariable() &&
			            			!triplePath.getObject().isVariable()) {
		            		triplePatternCountVcc++;
		            	}
		            }
		        }
		    }
		);
	}
	private void getJoinsInfo() {
		Element qPattern = getQueryPattern();
		final HashMap<String, Integer> ssJoins = new HashMap<String, Integer>();
		// This will walk through all parts of the query
		ElementWalker.walk(qPattern,
		    // For each element...
		    new ElementVisitorBase() {
		        // ...when it's a block of triples...
		        public void visit(ElementPathBlock el) {
		        	
		        	String subject = null;
		        	do {
		        		subject = null;
		        		// ...go through all the triples...
		        		Iterator<TriplePath> triples = el.patternElts();
			            while (triples.hasNext()) {
			            	TriplePath triplePath = triples.next();
			            	
		            		if (subject == null) {
		            			if (ssJoins.containsKey(triplePath.getSubject().toString())) {
		            				continue;
		            				//we've already processed this one 
		            			} else {
		            				subject = triplePath.getSubject().toString();
		            			}
		            		} else if (subject.equals(triplePath.getSubject().toString())) {
		            			if (ssJoins.containsKey(subject)) {
		            				ssJoins.put(subject, ssJoins.get(subject).intValue() + 1);
		            			} else {
		            				ssJoins.put(subject, 1);
		            			}
		            		}
			            }
		        	} while (subject != null);
		            
		        }
		    }
		);
		for (String subject: ssJoins.keySet()) {
			System.out.println(subject);
		}
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Query query = Query.create("SELECT * WHERE {<http://bla1> <http://pred2> ?jh.}");
		Op op = Algebra.compile(query) ;
		System.out.println(op);
	}
}
