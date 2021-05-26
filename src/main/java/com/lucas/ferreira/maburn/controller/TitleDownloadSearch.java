package com.lucas.ferreira.maburn.controller;

import com.lucas.ferreira.maburn.exceptions.SearchNotResultException;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchScraped;
import com.lucas.ferreira.maburn.model.search.SearchScraping;

public class TitleDownloadSearch {
	
	private SearchEngine engine;
	
	public TitleDownloadSearch(SearchEngine engine) {
		// TODO Auto-generated constructor stub
		this.engine = engine;
	}
	
	public String searchScraping(String value, Sites site) throws SearchNotResultException {
		
		SearchScraping searchScraping = new SearchScraping(value, engine, site);

		SearchScraped searchScraped = searchScraping.search();
		if (searchScraped == null || searchScraped.getResults().size() == 0) {
			throw new SearchNotResultException("Not Result found");
		}

		String bestResult = searchScraped.getResults().get(0);
		return bestResult;
	}
}
