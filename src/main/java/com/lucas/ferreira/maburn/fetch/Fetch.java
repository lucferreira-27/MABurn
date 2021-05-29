package com.lucas.ferreira.maburn.fetch;

import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;

import javafx.collections.ObservableList;

public interface Fetch {
	public  ObservableList<ItemScraped> fetch(Scraping scraping);
}
