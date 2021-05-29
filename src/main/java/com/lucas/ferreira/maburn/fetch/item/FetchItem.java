package com.lucas.ferreira.maburn.fetch.item;

import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;

import javafx.collections.ObservableList;

public class FetchItem {
	public ObservableList<ItemScraped> fetch(ListItemScraping listItemsScraping, List<String> listUrls) {
		
		
		ObservableList<ItemScraped> obsItemScrapeds = listItemsScraping.scrapeItems(listUrls);
		
		return obsItemScrapeds;

	}
}
