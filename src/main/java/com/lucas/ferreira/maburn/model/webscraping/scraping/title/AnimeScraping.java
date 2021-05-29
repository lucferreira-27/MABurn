package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.microsoft.playwright.Page;

public class AnimeScraping extends TitleScraping {

	private Sites site;
	
	
	public AnimeScraping(Sites site) {
		// TODO Auto-generated constructor stub
		super(site);
		this.site = site;

	}

	public Sites getSite() {
		return site;
	}


	@Override
	protected TitleScraped scrape(Page page, String script, Options options) {
		// TODO Auto-generated method stub
		List<String> episodios = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
		PageInfo pageInfo = fillPageInfo(page);
		String url = page.url();
		AnimeScraped animeScraped = new AnimeScraped(url, site, pageInfo,episodios);
		return animeScraped;
	}


}
