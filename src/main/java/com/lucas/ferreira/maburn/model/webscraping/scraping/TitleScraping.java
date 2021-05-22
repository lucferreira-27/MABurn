package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.AutoBrowser;
import com.lucas.ferreira.maburn.model.webscraping.CustomNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class TitleScraping extends Scraping {

	private Sites site;

	public TitleScraping(Sites site) {
		this.site = site;
		// TODO Auto-generated constructor stub
	}

	public TitleScraped scrapeTitle(String url) {
		// TODO Auto-generated method stub
		try {
			launch(false);
			Page page = newPage();

			Evaluate evaluate = new Evaluate();
			String script = evaluate.findTitleScript(site);
			RulesProperties rulesProperties = readScrapingSiteRules(site);
			Options options = getOptions(new TitleNavigateOptions(rulesProperties));

			navigate(url, page, options);
			TitleScraped titleScraped = scrape(page, script, options);
			screenshot(page);

			return titleScraped;

		} catch (Exception e) {
			return null;
		} finally {
			close();

		}

	}
	

	
	protected abstract TitleScraped scrape(Page page, String script, Options options);

}
