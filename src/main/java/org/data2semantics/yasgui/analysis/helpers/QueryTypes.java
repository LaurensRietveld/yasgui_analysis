package org.data2semantics.yasgui.analysis.helpers;

import org.data2semantics.yasgui.analysis.Collection;
import org.data2semantics.yasgui.analysis.Query;

public class QueryTypes extends AnalysisHelper {

	public QueryTypes(Collection collection) {
		super(collection);
	}

	public void calc() {
		int constructs = 0;
		int asks = 0;
		int selects = 0;
		int describes = 0;
		for (Query query: collection.getQueryCollection().getQueries()) {
			if (query.isConstructType()) {
				constructs += query.getCount();
			} else if (query.isAskType()) {
				asks += query.getCount();
			} else if (query.isDescribeType()) {
				describes += query.getCount();
			} else if (query.isSelectType()) {
				selects += query.getCount();
			} else {
				System.out.println("unknown query type: " + query.toString());
			}
		}
		System.out.println("ask: " + asks);
		System.out.println("constructs: " + constructs);
		System.out.println("selects: " + selects);
		System.out.println("describes: " + describes);
	}
}
