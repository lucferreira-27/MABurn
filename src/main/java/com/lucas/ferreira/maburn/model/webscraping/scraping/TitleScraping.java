package com.lucas.ferreira.maburn.model.webscraping.scraping;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.microsoft.playwright.Page;

public abstract class TitleScraping extends Scraping {

	private Sites site;

	public TitleScraping(Sites site) {
		this.site = site;
		// TODO Auto-generated constructor stub
	}

	public TitleScraped scrapeTitle(String url) {
		// TODO Auto-generated method stub
		try {
			launch(true);
			Page page = newPage();

			Evaluate evaluate = new Evaluate();
			String script = evaluate.findTitleScript(site);
			RulesProperties rulesProperties = readScrapingSiteRules(site);
			Options options = getOptions(new TitleNavigateOptions(rulesProperties));
			System.out.println("Title: " + url);
			navigate(url, page, options);
			TitleScraped titleScraped = scrape(page, script, options);
			System.out.println(titleScraped.getItemsScraped());

			return titleScraped;

		} catch (Exception e) {
			return null;
		} finally {
			close();

		}

	}
	

	
	protected abstract TitleScraped scrape(Page page, String script, Options options);

}
