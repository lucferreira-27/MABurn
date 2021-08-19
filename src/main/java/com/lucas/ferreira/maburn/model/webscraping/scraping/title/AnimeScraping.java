package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.sites.InteractSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.microsoft.playwright.Page;

public class AnimeScraping extends TitleScraping {

	private RegisteredSite registeredSite;
	
	


	public RegisteredSite getSite() {
		return registeredSite;
	}


	@Override
	protected TitleScraped scrape(Page page,SiteValues siteValues) {
		

		InteractSite interactSite = new InteractSite(page);
		SiteResult siteResult = interactSite.get(siteValues);
		
		AnimeScraped animeScraped = new AnimeScraped(siteResult);
		
		this.registeredSite = siteValues.getRegisteredSite();
		siteResult.getPageInfo().setTotalItems(siteResult.getItemsValues().size());
		siteResult.getPageInfo().setRegisteredSite(registeredSite);

		return animeScraped;
	}


}
