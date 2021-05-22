package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.AutoBrowser;
import com.lucas.ferreira.maburn.model.webscraping.CustomNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.microsoft.playwright.Page;

public abstract class Scraping extends AutoBrowser {
	

	protected Options getOptions(CustomNavigateOptions customNavigateOptions) {
		Options options = customNavigateOptions.getOptions();
		return options;
	}

	protected void navigate(String url, Page page, Options options) {
		PageNavigate pageNavigate = new PageNavigate(page, options);
		
		pageNavigate.navigate(url);
	}

	protected RulesProperties readScrapingSiteRules(Sites site) {
		ScrapingRuler ruler = new ScrapingRuler();
		RulesProperties rulesProperties = null;
		try {
			rulesProperties = ruler.readPropertiesFromSite(site);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rulesProperties;
	}
	protected RulesProperties readScrapingSearchRules(SearchEngine engine) {
		ScrapingRuler ruler = new ScrapingRuler();
		RulesProperties rulesProperties = null;
		try {
			rulesProperties = ruler.readPropertiesFromSearchEngine(engine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rulesProperties;
	}
}
