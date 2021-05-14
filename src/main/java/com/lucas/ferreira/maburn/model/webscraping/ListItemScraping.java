package com.lucas.ferreira.maburn.model.webscraping;

import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraped;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

public interface ListItemScraping {
	public ObservableList<ItemScraped> scrapeItems(List<String> urls);
	public BooleanProperty isScrapingDone();
}
