package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.microsoft.playwright.Page;

public class MangaScraping extends TitleScraping {



	@Override
	protected TitleScraped scrape(Page page, SiteValues siteValues) {

		return new MangaScraped(scrapeTitle(page,siteValues));

	}

}
