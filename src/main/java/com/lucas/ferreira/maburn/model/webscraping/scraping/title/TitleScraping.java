package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.event.ClickInteractEvent;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.Page;

public abstract class TitleScraping extends Scraping {

	private Sites site;

	public TitleScraping(Sites site) {
		this.site = site;
		
	}
	public TitleScraped scrapeTitle(String url) {
		
		try {
			launch(true);
			Page page = newPage();

			Evaluate evaluate = new Evaluate();
			String script = evaluate.findTitleScript(site);
			RulesProperties rulesProperties = readScrapingSiteRules(site);
			Options options = getOptions(new TitleNavigateOptions(rulesProperties));
			
			navigate(url, page, options);
			TitleScraped titleScraped = scrape(page, script, options);
			

			return titleScraped;

		} catch (Exception e) {
			return null;
		} finally {
			close();

		}

	}
	

	
	protected abstract TitleScraped scrape(Page page, String script, Options options);

}
