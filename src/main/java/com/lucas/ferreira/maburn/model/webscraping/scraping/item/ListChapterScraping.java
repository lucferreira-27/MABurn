package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.navigate.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;

import javafx.collections.ObservableList;

public class ListChapterScraping extends ListItemScraping {

	private Sites site;

	public ListChapterScraping(Sites site, MyBrowser myBrowser) {
		
		super(myBrowser);
		this.site = site;
	}

	@Override
	public ItemScraped startItemScraping(String url, BrowserPage browserPage) {
			ItemScraping chapterScraping = new ChapterScraping(site, browserPage.getContext());
			ItemScraped itemScraped = chapterScraping.scrapeItem(url);
			itemScraped.setSite(site);
			browserPage.setAvailable(true);
			return itemScraped;


	}

}
