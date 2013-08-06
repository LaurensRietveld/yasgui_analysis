package org.data2semantics.yasgui.analysis.helpers;

import org.data2semantics.yasgui.analysis.QueryCollection;

public abstract class AnalysisHelper {
	
	protected QueryCollection queryCollection;
	
	public AnalysisHelper(QueryCollection queryCollection) {
		this.queryCollection = queryCollection;
	}
	public abstract void calc() throws Exception;
}
