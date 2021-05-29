package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class EpisodeScraping extends ItemScraping {

	public EpisodeScraping(Sites site, BrowserContext context) {

		super(site, context);
	}

	protected ItemScraped scrape(Page page, String script, Options options) {

		try{
		String video = (String) page.evaluate(script, options.getSelectQuery());

		Map<Definition, String> links = new HashMap<Definition, String>();
		links.put(Definition.DEFINITION_1080, video);
		EpisodeScraped episodeScraped = new EpisodeScraped(links);
		return episodeScraped;
		}catch (Exception e) {
			return itemScrapedWithException(e);
		}
	}


	@Override
	protected ItemScraped itemScrapedWithException(Exception e) {
		return new EpisodeScraped(e);
	}

}
