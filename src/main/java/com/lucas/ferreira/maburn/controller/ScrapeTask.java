package com.lucas.ferreira.maburn.controller;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.ListItemScraping;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ScrapeTask implements Callable<ItemScraped> {

	private String url;
	private ObservableList<ItemScraped> obsItems;
	private BrowserPage browserPage;
	private ListItemScraping listItemScraping;

	public ScrapeTask(String url, ObservableList<ItemScraped> obsItems, BrowserPage browserPage,
			ListItemScraping listItemScraping) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.obsItems = obsItems;
		this.browserPage = browserPage;
		this.listItemScraping = listItemScraping;
		System.out.println(listItemScraping);
	}


	@Override
	public ItemScraped call() throws Exception {
		System.out.println("call");
		ItemScraped itemScraped = listItemScraping.startItemScraping(obsItems, url, browserPage);

		return itemScraped;
	}



}
