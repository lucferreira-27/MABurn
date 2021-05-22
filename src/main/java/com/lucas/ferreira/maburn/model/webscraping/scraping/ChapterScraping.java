package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ChapterScraping  extends ItemScraping {


	
	public ChapterScraping(Sites site, BrowserContext context) {
		// TODO Auto-generated constructor stub
		super(site, context);

	}

	
	protected ItemScraped scrape(Page page, String script, Options options) {
		@SuppressWarnings("unchecked")
		List<String> pages = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());

		ChapterScraped chapterScraped = new ChapterScraped(pages);

		return chapterScraped;
	}
}
