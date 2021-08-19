package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.InteractSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public abstract class ItemScraping extends Scraping {

	private BrowserContext context;
	private RegisteredSite registeredSite;

	public ItemScraping(RegisteredSite registeredSite, BrowserContext context) {

		this.registeredSite = registeredSite;
		this.context = context;
	}

	public ItemScraped scrapeItem(SiteValues siteValues) {

		try {
			Page page = context.newPage();
//			Evaluate evaluate = new Evaluate();
//			String script = evaluate.findItemScript(site);
			InteractSite interactSite = new InteractSite(page);

			SiteResult siteResult = interactSite.get(siteValues);
//			RulesProperties rulesProperties = readScrapingSiteRules(site);
//			Options options = getOptions(new ItemNavigateOptions(rulesProperties));
//
//			navigate(url, page, options);

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
