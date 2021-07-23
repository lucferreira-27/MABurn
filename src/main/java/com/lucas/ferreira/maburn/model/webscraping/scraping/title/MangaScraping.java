package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.microsoft.playwright.Page;

public class MangaScraping   extends TitleScraping {
	
	private Sites site;
	public MangaScraping(Sites site) {
		
		
		super(site);
		
	}
	
	public Sites getSite() {
		return site;
	}

	@Override
	protected TitleScraped scrape(Page page, String script, Options options) {
		
		List<String> chapters = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
		PageInfo pageInfo = fillPageInfo(page);
		String url = page.url();
		MangaScraped mangaScraped = new MangaScraped(url, site, pageInfo,chapters);
		pageInfo = fillMorePageInfo(pageInfo, mangaScraped);

		return mangaScraped;
	}
	

	

}
