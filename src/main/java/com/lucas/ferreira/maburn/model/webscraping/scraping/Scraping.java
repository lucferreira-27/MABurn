package com.lucas.ferreira.maburn.model.webscraping.scraping;

import com.lucas.ferreira.maburn.model.browser.AutoBrowser;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.sites.InteractSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.lucas.ferreira.maburn.model.webscraping.event.ClickInteractEvent;
import com.lucas.ferreira.maburn.model.webscraping.navigate.CustomNavigateOptions;
import com.microsoft.playwright.Page;

import java.io.IOException;

public abstract class Scraping extends AutoBrowser {


	protected Options getOptions(CustomNavigateOptions customNavigateOptions) {
		return customNavigateOptions.getOptions();
	}
	protected void navigate(String url, Page page, Options options) throws Exception {
		PageNavigate pageNavigate = new PageNavigate(page, options);
		pageNavigate.navigate(url);
	}
	protected void click(Page page, String selector) {
		ClickInteractEvent clickInteractEvent = new ClickInteractEvent(page);
		clickInteractEvent.event(selector);

	}
	protected SiteResult get(SiteValues siteValues, Page page){
		InteractSite interactSite = new InteractSite(page);
		return interactSite.get(siteValues);
	}

	protected void setPageInfos(SiteResult siteResult, RegisteredSite registeredSite){
		siteResult.getPageInfo().setTotalItems(siteResult.getItemsValues().size());
		siteResult.getPageInfo().setRegisteredSite(registeredSite);
	}

	protected RulesProperties readScrapingSearchRules(SearchEngine engine) {
		ScrapingRuler ruler = new ScrapingRuler();
		RulesProperties rulesProperties = null;
		try {
			rulesProperties = ruler.readPropertiesFromSearchEngine(engine);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		return rulesProperties;
	}
}
