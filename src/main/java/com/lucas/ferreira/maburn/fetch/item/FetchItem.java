package com.lucas.ferreira.maburn.fetch.item;

import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import javafx.collections.ObservableList;

public class FetchItem {
	public ObservableList<ScrapingWork> fetch(ListItemScraping listItemsScraping, List<String> urls) {
		
		
		ObservableList<ScrapingWork> obsItemScrapingWork = listItemsScraping.scrapeItems(urls);
		System.out.println(obsItemScrapingWork);
		return obsItemScrapingWork;

	}
}
