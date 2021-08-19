package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.exceptions.SearchNotResultException;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.scraping.SearchScraping;
import com.lucas.ferreira.maburn.model.webscraping.search.SearchScraped;

public class TitleDownloadSearch {
	
	private SearchEngine engine;
	
	public TitleDownloadSearch(SearchEngine engine) {
		
		this.engine = engine;
	}
	
	public String searchScraping(String value, RegisteredSite site) throws SearchNotResultException {
		
		SearchScraping searchScraping = new SearchScraping(value, engine, site);

		SearchScraped searchScraped = searchScraping.search();
		if (searchScraped == null || searchScraped.getResults().size() == 0) {
			throw new SearchNotResultException("Not Result found");
		}

		String bestResult = searchScraped.getResults().get(0);
		return bestResult;
	}
}
