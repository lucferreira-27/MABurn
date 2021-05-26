package com.lucas.ferreira.maburn.fetch;

import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;

import javafx.collections.ObservableList;

public interface Fetch {
	public  ObservableList<ItemScraped> fetch(Scraping scraping);
}
