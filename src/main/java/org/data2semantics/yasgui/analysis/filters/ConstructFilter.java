package org.data2semantics.yasgui.analysis.filters;

import org.data2semantics.yasgui.analysis.Query;


public class ConstructFilter implements Filter {
	public boolean filter(Query query) {
		if (query.isConstructType()) {
			return true;
		} else {
			return false;
		}
	}
}
