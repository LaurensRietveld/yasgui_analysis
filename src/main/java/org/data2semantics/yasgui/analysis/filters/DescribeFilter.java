package org.data2semantics.yasgui.analysis.filters;

import org.data2semantics.yasgui.analysis.Query;



public class DescribeFilter implements Filter {
	public boolean filter(Query query) {
		if (query.isDescribeType()) {
			return true;
		} else {
			return false;
		}
	}
}
