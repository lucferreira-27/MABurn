package com.lucas.ferreira.maburn.fetch;

import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraping;

import javafx.collections.ObservableList;

public class ItemFetcher {
	public ObservableList<ItemScraped> fetch(ItemScraping itemScraping, String url, Fetch fetch) {
		
		
		ObservableList<ItemScraped> obsItems = fetch.fetch(itemScraping);

		return obsItems;

	}
	
}
