package org.data2semantics.yasgui.analysis.helpers;

import org.data2semantics.yasgui.analysis.Collection;

public abstract class AnalysisHelper {
	
	protected Collection collection;
	
	public AnalysisHelper(Collection collection) {
		this.collection = collection;
	}
	public abstract void calc() throws Exception;
}
