package org.data2semantics.yasgui.analysis.helpers;

import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.QueryCollection;

public class QueryTypes extends AnalysisHelper {

	public QueryTypes(QueryCollection queryCollection) {
		super(queryCollection);
	}

	public void calc() {
		int constructs = 0;
		int asks = 0;
		int selects = 0;
		int describes = 0;
		for (Query query: queryCollection.getQueries()) {
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
