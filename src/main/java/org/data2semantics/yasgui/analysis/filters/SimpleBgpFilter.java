package org.data2semantics.yasgui.analysis.filters;

import java.util.Iterator;

import org.data2semantics.yasgui.analysis.Query;

import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.syntax.ElementWalker;

public class SimpleBgpFilter implements Filter {
	boolean isSimpleBgp = true;
	boolean onlyVariables = true;
	public boolean filter(final Query query) {
		isSimpleBgp = true;
		Element qPattern = query.getQueryPattern();
		if (qPattern == null) {
			//ok, this query doesnt have query pattern (e.g. DESCRIBE <http://bla>) and is extremely simple
			//yet, keep it. we only want to remove the simple queries with 1 triple pattern ?x ?y ?z
			isSimpleBgp = false;
		} else {
			// This will walk through all parts of the query
			ElementWalker.walk(qPattern,
			    // For each element...
			    new ElementVisitorBase() {
			        // ...when it's a block of triples...
			        public void visit(ElementPathBlock el) {
			            // ...go through all the triples...
			            Iterator<TriplePath> triples = el.patternElts();
			            while (triples.hasNext()) {
			            	TriplePath triplePath = triples.next();
			            	if (!triplePath.isTriple() || //is not a triple when a path expression is used in pred location
			            			!triplePath.getObject().isVariable() || 
			            			!triplePath.getSubject().isVariable() ||
			            			!triplePath.getPredicate().isVariable()) {
			            		//not everything in bgp is var
			            		isSimpleBgp = false;
			            		break;
			            	}

			            }
			        }
			    }
			);
		}
		return isSimpleBgp;
	}
	
	public static void main(String[] args)  {
		try {
			Query queryWrapper = (Query)(QueryFactory.create("ASK\n" + 
					"WHERE\n" + 
					"  { ?s ?p ?o }"));
			SimpleBgpFilter filter = new SimpleBgpFilter();

			System.out.println(filter.filter(queryWrapper));
//			ArrayList<QueryWrapper> queries = qaldQueries.getQueries();
			
//			for (QueryWrapper query: queries) {
//				System.out.println(Integer.toString(query.getQueryId()));
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
