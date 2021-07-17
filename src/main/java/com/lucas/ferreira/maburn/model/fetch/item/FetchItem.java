package com.lucas.ferreira.maburn.model.fetch.item;

import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;

import javafx.collections.ObservableList;

public class FetchItem {
	public ObservableList<ScrapingWork> fetch(ListItemScraping listItemsScraping, List<ScrapingWork> scrapingWorks) {
		
		
		ObservableList<ScrapingWork> obsItemScrapingWork = listItemsScraping.scrapeItems(scrapingWorks);
		System.out.println(obsItemScrapingWork);
		return obsItemScrapingWork;

	}
}
