package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.Page;

public abstract class TitleScraping extends Scraping {

	

	public TitleScraped scrapeTitle(SiteValues siteValues) {
		
		try {
			
			markTime.begin();
			launch(false);
			Page page = newPage();

			TitleScraped titleScraped = scrape(page, siteValues);
			titleScraped.getPageInfo().setTime(markTime.end());
			return titleScraped;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();

		}

	}
	

	
	protected abstract TitleScraped scrape(Page page, SiteValues siteValues);

}
