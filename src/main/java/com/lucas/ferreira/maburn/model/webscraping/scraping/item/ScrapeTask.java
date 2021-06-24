package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.webscraping.browser.BrowserPage;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ScrapeTask implements Callable<ItemScraped> {

	private String url;
	private ObservableList<ScrapingWork> obsItems;
	private BrowserPage browserPage;
	private ListItemScraping listItemScraping;
	private ScrapingWork scrapingWork ;

	public ScrapeTask(String url, ObservableList<ScrapingWork> obsItems, BrowserPage browserPage,
			ListItemScraping listItemScraping) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.obsItems = obsItems;
		this.browserPage = browserPage;
		this.listItemScraping = listItemScraping;
	}

	@Override
	public ItemScraped call() throws Exception {
		scrapingWork = new ScrapingWork(url);
		scrapingWork.setScrapeState(ScrapeState.WORKING);
		obsItems.add(scrapingWork);
		ItemScraped itemScraped = listItemScraping.startItemScraping(url, browserPage);
		scrapingWork.setWorkResult(itemScraped);

		if (itemScraped.getException() == null)
			scrapingWork.setScrapeState(ScrapeState.SUCCEED);
		else
			scrapingWork.setScrapeState(ScrapeState.FAILED);
		System.out.println("call: " + scrapingWork.getScrapeState());
		return itemScraped;
	}

}
