package com.lucas.ferreira.maburn.model.webscraping.search;

import java.util.ArrayList;
import java.util.List;

public class SearchScraped {
	
	private List<String> listResults = new ArrayList<String>();
	
	
	public SearchScraped(List<String> listResults) {
		
		this.listResults = listResults;
	}
	
	
	public List<String> getResults() {
		return listResults;
	}
}
