package com.lucas.ferreira.maburn.model.update;

import java.util.List;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class CollectionUpdate {
	private WebScraping scraping;
	private Collections collections;

	public CollectionUpdate(Collections collection) {
		this.collections = collection;
	}

	public boolean update() {

		return false;
	}

	public void synchronizeCollection() {

		scraping = new GoyabuScraping();
		collections.getItens().forEach(item -> {
			List<SearchResult> results = scraping.fetchSearchTitle(item.getName());
			results.forEach(result -> {
				CustomLogger.log(result.getUrl());
			});
		});
	}

}
