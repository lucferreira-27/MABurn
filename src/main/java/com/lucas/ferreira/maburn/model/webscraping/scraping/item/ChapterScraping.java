package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.microsoft.playwright.BrowserContext;

public class ChapterScraping extends ItemScraping {

	public ChapterScraping(RegisteredSite registeredSite, BrowserContext context) {
		
		super(registeredSite, context);

	}

	protected ItemScraped scrape(SiteResult siteResult) {
		try {
			return new ChapterScraped(siteResult);
		} catch (Exception e) {
			return itemScrapedWithException(e);
		}
	}


	@Override
	protected ItemScraped itemScrapedWithException(Exception e) {
		
		return new  ChapterScraped(e);
	}
}
