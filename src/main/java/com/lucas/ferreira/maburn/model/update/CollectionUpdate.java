package com.lucas.ferreira.maburn.model.update;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;

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
			List<SearchTitleWebData> results = scraping.fetchSearchTitle(item.getName());
			results.forEach(result -> {
				System.out.println(result.getUrl());
			});
		});
	}

}
