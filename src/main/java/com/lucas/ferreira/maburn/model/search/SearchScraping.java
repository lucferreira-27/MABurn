package com.lucas.ferreira.maburn.model.search;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.Page;

public class SearchScraping extends Scraping {

	private SearchEngine engine;
	private Sites site;
	private String title;

	public SearchScraping(String title, SearchEngine engine, Sites site) {
		// TODO Auto-generated constructor stub
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
			System.out.println("Google: " + url);
			navigate(url, page, options);
			@SuppressWarnings("unchecked")
			List<String> results = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
			System.out.println(results.size());
			SearchScraped searchScraped = new SearchScraped(results);
			return searchScraped;

		} catch (Exception e) {
			return null;
		} finally {
			close();

		}
	}
}
