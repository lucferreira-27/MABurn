package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public abstract class ItemScraping extends Scraping {

	private final BrowserContext context;
	private final RegisteredSite registeredSite;

	public ItemScraping(RegisteredSite registeredSite, BrowserContext context) {
		this.registeredSite = registeredSite;
		this.context = context;
	}

	public ItemScraped scrapeItem(SiteValues siteValues) {

		try {
			Page page = context.newPage();

			SiteResult siteResult = get(siteValues,page);
			ItemScraped itemScraped = scrape(siteResult);
			itemScraped.setRegisteredSite(registeredSite);
			itemScraped.setSiteResult(siteResult);

			page.close();

			return itemScraped;
		} catch (Exception e) {

			return itemScrapedWithException(e);
		}
	}

	protected abstract ItemScraped scrape(SiteResult siteResult);

	protected abstract ItemScraped itemScrapedWithException(Exception e);

}
