package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ChapterScraping extends ItemScraping {

	public ChapterScraping(Sites site, BrowserContext context) {
		
		super(site, context);

	}

	protected ItemScraped scrape(Page page, String script, Options options) {
		try {
			@SuppressWarnings("unchecked")
			List<String> pages = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());

			ChapterScraped chapterScraped = new ChapterScraped(pages);

			return chapterScraped;
		} catch (Exception e) {
			return itemScrapedWithException(e);
		}
	}


	@Override
	protected ItemScraped itemScrapedWithException(Exception e) {
		
		return new  ChapterScraped(e);
	}
}
