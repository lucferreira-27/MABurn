package com.lucas.ferreira.maburn.model.webscraping.scraping;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.search.SearchScraped;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchScraping extends Scraping {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private SearchEngine engine;
	private RegisteredSite registeredSite;
	private String title;

	public SearchScraping(boolean headless,String title, SearchEngine engine, RegisteredSite registeredSite) {
		super(headless);
		this.engine = engine;
		this.title = title;
		this.registeredSite = registeredSite;
	}

	public SearchScraped search() {
		try {
			LOGGER.info("Search: " + title + " in " + registeredSite.getSiteConfig().getHomeUrl());
			launch();
			Page page = newPage();

			Evaluate evaluate = new Evaluate();
			String script = evaluate.findSearchScript(engine);
			RulesProperties rulesProperties = readScrapingSearchRules(engine);
			Options options = getOptions(new TitleNavigateOptions(rulesProperties));

			String url = engine.getUrl() + title + engine.getFilter() + registeredSite.getSiteConfig().getHomeUrl();

			navigate(url, page, options);
			@SuppressWarnings("unchecked")
			List<String> results = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());

			return new SearchScraped(results);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();

		}
	}
}
