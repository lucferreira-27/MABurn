package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.navigate.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;

import java.io.IOException;

public class ListEpisodeScraping extends ListItemScraping {
	private XmlConfigurationOrchestrator xmlConfigurationOrchestrator = new XmlConfigurationOrchestrator();

	private RegisteredSite registeredSite;

	public ListEpisodeScraping(RegisteredSite registeredSite, MyBrowser myBrowser) {
		
		super(myBrowser);
		this.registeredSite = registeredSite;
	}

	@Override
	public ItemScraped startItemScraping(SiteValues siteResult, BrowserPage browserPage) {
		ItemScraping episodeScraping = new EpisodeScraping(registeredSite, browserPage.getContext());
		ItemScraped itemScraped = episodeScraping.scrapeItem(siteResult);
		itemScraped.setRegisteredSite(registeredSite);
		browserPage.setAvailable(true);
		return itemScraped;

	}

}
