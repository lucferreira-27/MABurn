package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.browser.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.browser.MyBrowser;

import javafx.collections.ObservableList;

public class ListEpisodeScraping extends ListItemScraping {

	private Sites site;

	public ListEpisodeScraping(Sites site, MyBrowser myBrowser) {
		
		super(myBrowser);
		this.site = site;
	}

	@Override
	public ItemScraped startItemScraping(String url, BrowserPage browserPage) {
	
		ItemScraping episodeScraping = new EpisodeScraping(site, browserPage.getContext());
		ItemScraped itemScraped = episodeScraping.scrapeItem(url);
		itemScraped.setSite(site);
		browserPage.setAvailable(true);
		return itemScraped;

	}

}
