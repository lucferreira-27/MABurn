package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.Page;

public abstract class TitleScraping extends Scraping {

	private RegisteredSite registeredSite;

	public TitleScraping(boolean headless) {
		super(headless);
	}

	protected RegisteredSite getSite() {
		return registeredSite;
	}

	public TitleScraped scrapeTitle(SiteValues siteValues) {
		
		try {
			markTime.begin();
			launch();
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

	protected SiteResult scrapeTitle(Page page, SiteValues siteValues){
		this.registeredSite = siteValues.getRegisteredSite();

		SiteResult siteResult = get(siteValues,page);

		setPageInfos(siteResult, registeredSite);
		return siteResult;
	}
}
