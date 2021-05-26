package com.lucas.ferreira.maburn.model.webscraping.scraping;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.MyBrowser;

import javafx.collections.ObservableList;

public class ListChapterScraping extends ListItemScraping {

	private Sites site;

	public ListChapterScraping(Sites site, MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		super(myBrowser);
		this.site = site;
	}

	@Override
	public ItemScraped startItemScraping(ObservableList<ItemScraped> obsItems, String url, BrowserPage browserPage) {
			ItemScraping chapterScraping = new ChapterScraping(site, browserPage.getContext());
			ItemScraped itemScraped = chapterScraping.scrapeItem(url);
			obsItems.add(itemScraped);
			browserPage.setAvailable(true);
			return itemScraped;


	}

}
