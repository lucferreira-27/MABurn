package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.navigate.ItemNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.scraping.Scraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Frame.AddScriptTagOptions;

public abstract class  ItemScraping extends Scraping {
	
	private Sites site;
	
	private BrowserContext context;
	
	public ItemScraping(Sites site, BrowserContext context) {
		
		this.site = site;
		this.context = context;
	}
	
	
	public ItemScraped scrapeItem(String url) {
		
		try {
		Page page = context.newPage();
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findItemScript(site); 
		
		RulesProperties rulesProperties = readScrapingSiteRules(site);
		Options options = getOptions(new ItemNavigateOptions(rulesProperties));


		navigate(url, page, options);
		
		ItemScraped itemScraped = scrape(page, script, options);
		itemScraped.setUrl(url);
		
		page.close();
		
		return itemScraped;
	} catch (Exception e) {
			
		return itemScrapedWithException(e);
		}
	}


	protected abstract  ItemScraped scrape(Page page, String script, Options options);
	protected abstract  ItemScraped itemScrapedWithException(Exception e);



	
}
