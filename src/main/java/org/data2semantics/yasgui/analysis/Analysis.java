package org.data2semantics.yasgui.analysis;

import org.data2semantics.yasgui.analysis.filters.SimpleBgpFilter;
import org.data2semantics.yasgui.analysis.helpers.AccessibilityStats;
import org.data2semantics.yasgui.analysis.helpers.AnalysisHelper;
import org.data2semantics.yasgui.analysis.helpers.CkanStats;
import org.data2semantics.yasgui.analysis.helpers.QueryTypes;
import org.data2semantics.yasgui.analysis.loaders.CsvLoader;
import org.data2semantics.yasgui.analysis.loaders.Loader;

public class Analysis {
	Collection collection;
	AnalysisHelper analysis;
	public void runAnalysis() throws Exception {
		CsvLoader loader = new CsvLoader(Loader.InputType.OLD_ENDPOINTS, Loader.InputType.OLD_QUERIES, Loader.InputType.NEW_QUERIES);
		loader.setFilters(new SimpleBgpFilter());
		loader.load();
		collection = loader.getCollection();
		System.out.println(loader.getCollection().getQueryCollection().toString());
		System.out.println(loader.getCollection().getEndpointCollection().toString());
		
//		runQueryAnalysis();
		runEndpointAnalysis();
	}
	

	private void runQueryAnalysis() throws Exception {
		analysis = new QueryTypes(collection);
		analysis.calc();
	}
	private void runEndpointAnalysis() throws Exception {
		
		
//		analysis = new CkanStats(collection);
//		analysis.calc();
		analysis = new AccessibilityStats(collection);
		analysis.calc();
	}
	
	public static void main(String[] args) throws Exception {
		Analysis analysis = new Analysis();
		
		analysis.runAnalysis();
	}
}
