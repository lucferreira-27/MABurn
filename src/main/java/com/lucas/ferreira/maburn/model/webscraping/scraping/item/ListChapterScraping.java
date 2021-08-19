package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.navigate.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;

public class ListChapterScraping extends ListItemScraping {

	private RegisteredSite registeredSite;

	public ListChapterScraping(RegisteredSite registeredSite, MyBrowser myBrowser) {
		
		super(myBrowser);
		this.registeredSite = registeredSite;
	}

	@Override
	public ItemScraped startItemScraping(SiteValues siteValues, BrowserPage browserPage) {
			ItemScraping chapterScraping = new ChapterScraping(registeredSite, browserPage.getContext());
			ItemScraped itemScraped = chapterScraping.scrapeItem(siteValues);
			itemScraped.setRegisteredSite(registeredSite);
			browserPage.setAvailable(true);
			return itemScraped;


	}

}
