package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.microsoft.playwright.Page;

public class AnimeScraping extends TitleScraping {

	public AnimeScraping(boolean headless) {
		super(headless);
	}

	@Override
	protected TitleScraped scrape(Page page,SiteValues siteValues) {

		return new AnimeScraped(scrapeTitle(page,siteValues));
	}


}
