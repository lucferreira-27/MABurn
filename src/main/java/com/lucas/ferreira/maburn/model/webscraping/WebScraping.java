package com.lucas.ferreira.maburn.model.webscraping;

import java.util.List;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.BingSearch;
import com.lucas.ferreira.maburn.model.search.GoogleSearch;
import com.lucas.ferreira.maburn.model.search.SearchEngine;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.util.CustomLogger;

public abstract class WebScraping {

	public abstract TitleWebData fecthTitle(TitleWebData titleWebData) throws WebScrapingException;

	public abstract ItemWebData fecthItem(ItemWebData itemWebData) throws WebScrapingException;

	public abstract List<SearchResult> fetchSearchTitle(String querry) throws WebScrapingException;
	
	
	protected String search(String querry, Sites site, boolean filter) {
		SearchEngine engine = new GoogleSearch(querry, site);
		return engine.search();
	}
	

	protected String bingSearch(String querry, Sites site, boolean filter) {
		BingSearch bingSearch = new BingSearch(querry, site);
		if (filter) {
			return bingSearch.search();

		} else {
			return bingSearch.searchNoFilter();
		}
	}
	
	protected String googleSearch(String querry, Sites site, boolean filter) {
		GoogleSearch googleSearch = new GoogleSearch(querry, site);
		if (filter) {
			return googleSearch.search();

		} else {
			return googleSearch.searchNoFilter();
		}
	}

	protected boolean isTitlePage(String url, String expectedUrl) {
		CustomLogger.log(url);
		CustomLogger.log(expectedUrl);
		return url.contains(expectedUrl);
	}

	public abstract Sites getSite();
}
