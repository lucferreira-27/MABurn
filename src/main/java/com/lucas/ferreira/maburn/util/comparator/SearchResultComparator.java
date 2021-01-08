package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.util.StringUtil;

public class SearchResultComparator implements Comparator<SearchResult> {
	private final String TARGET_RESULT;

	public SearchResultComparator(String targetResult) {

		TARGET_RESULT = targetResult;
	}

	@Override
	public int compare(SearchResult o1, SearchResult o2) {
		// TODO Auto-generated method stub
		return checkLikeness(o2.getName()) - checkLikeness(o1.getName());
	}
	
	public int checkLikeness(String str) {
		
		return (int)( StringUtil.similarity(str, TARGET_RESULT) * 10);
	}
	

	
	
	
	

}
