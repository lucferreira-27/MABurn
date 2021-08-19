package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.SiteValues;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ScrapingWork {
	private ScrapeState scrapeState = ScrapeState.WAITING;
	private ObjectProperty<ScrapeState> propertyScrapeState = new SimpleObjectProperty<ScrapeState>(ScrapeState.WAITING);
	private SiteValues siteValues;
	private ItemScraped workResult;
	
	public ScrapingWork(SiteValues siteValues) {
		this.siteValues = siteValues;
	}
	
	public ScrapeState getScrapeState() {
		return scrapeState;
	}

	public void setScrapeState(ScrapeState scrapeState) {
		this.scrapeState = scrapeState;
		propertyScrapeState.set(scrapeState);

	}

	public ObjectProperty<ScrapeState> getPropertyScrapeState() {
		return propertyScrapeState;
	}

	public ItemScraped getWorkResult() throws Exception {
		if (scrapeState != ScrapeState.WORKING)
			return workResult;
		throw new Exception("Scraping still in working [" + scrapeState + "]");
	}

	public void setWorkResult(ItemScraped workResult) {
		this.workResult = workResult;
	}
	
	public SiteValues getSiteValues() {
		return siteValues;
	}
}
