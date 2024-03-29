package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.webscraping.navigate.BrowserPage;

public class ScrapeTask implements Callable<ItemScraped> {

	private BrowserPage browserPage;
	private ListItemScraping listItemScraping;
	private ScrapingWork scrapingWork ;

	public ScrapeTask(ScrapingWork scrapingWork, BrowserPage browserPage,
			ListItemScraping listItemScraping) {
		
		this.scrapingWork = scrapingWork;
		this.browserPage = browserPage;
		this.listItemScraping = listItemScraping;
	}

	@Override
	public ItemScraped call() throws Exception {
		scrapingWork.setScrapeState(ScrapeState.WORKING);
		ItemScraped itemScraped = listItemScraping.startItemScraping(scrapingWork.getSiteValues(), browserPage);
		scrapingWork.setWorkResult(itemScraped);

		if (itemScraped.getException() == null)
			scrapingWork.setScrapeState(ScrapeState.SUCCEED);
		else
			scrapingWork.setScrapeState(ScrapeState.FAILED);
		return itemScraped;
	}

}
