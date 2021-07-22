package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.search.SearchScraped;
import com.microsoft.playwright.Page;

public class SearchScraping extends Scraping {

	private SearchEngine engine;
	private Sites site;
	private String title;

	public SearchScraping(String title, SearchEngine engine, Sites site) {
		
		this.engine = engine;
		this.title = title;
		this.site = site;
	}

	public SearchScraped search() {
		try {
			launch(true);
			Page page = newPage();

			Evaluate evaluate = new Evaluate();
			String script = evaluate.findSearchScript(engine);
			RulesProperties rulesProperties = readScrapingSearchRules(engine);
			Options options = getOptions(new TitleNavigateOptions(rulesProperties));

			String url = engine.getUrl() + title + engine.getFilter() + site.getUrl();
			
			navigate(url, page, options);
			@SuppressWarnings("unchecked")
			List<String> results = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
			
			SearchScraped searchScraped = new SearchScraped(results);
			return searchScraped;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();

		}
	}
}
