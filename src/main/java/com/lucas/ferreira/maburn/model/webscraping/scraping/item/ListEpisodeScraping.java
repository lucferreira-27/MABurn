package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.browser.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.browser.MyBrowser;

import javafx.collections.ObservableList;

public class ListEpisodeScraping extends ListItemScraping {

	private Sites site;

	public ListEpisodeScraping(Sites site, MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		super(myBrowser);
		this.site = site;
	}

	@Override
	public ItemScraped startItemScraping(ObservableList<ItemScraped> obsItems, String url, BrowserPage browserPage) {
	
		ItemScraping episodeScraping = new EpisodeScraping(site, browserPage.getContext());
		ItemScraped itemScraped = episodeScraping.scrapeItem(url);
		obsItems.add(itemScraped);
		browserPage.setAvailable(true);
		return itemScraped;

	}

}