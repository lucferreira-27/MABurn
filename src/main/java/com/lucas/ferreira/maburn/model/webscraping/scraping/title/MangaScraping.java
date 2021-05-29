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
		// TODO Auto-generated constructor stub
		
		super(site);
		
	}
	
	public Sites getSite() {
		return site;
	}

	@Override
	protected TitleScraped scrape(Page page, String script, Options options) {
		// TODO Auto-generated method stub
		List<String> chapters = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
		PageInfo pageInfo = fillPageInfo(page);
		String url = page.url();
		MangaScraped mangaScraped = new MangaScraped(url, site, pageInfo,chapters);
		return mangaScraped;
	}
	

	

}
