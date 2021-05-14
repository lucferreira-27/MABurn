package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.AutoBrowser;
import com.lucas.ferreira.maburn.model.webscraping.CustomNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.ItemNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.lucas.ferreira.maburn.model.webscraping.scraping.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.ItemScraped;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

public class ChapterScraping  implements ItemScraping {

	private Sites site;
	
	private BrowserContext context;
	
	public ChapterScraping(Sites site, BrowserContext context) {
		// TODO Auto-generated constructor stub
		this.site = site;
		this.context = context;

	}

	public Sites getSite() {
		return site;
	}

	@Override
	public ItemScraped scrapeItem(String url) {
		// TODO Auto-generated method stub
	//	launch();
		Page page = context.newPage();
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findItemScript(site);
		ScrapingRuler ruler = new ScrapingRuler();
		RulesProperties rulesProperties = null;
		try {
			rulesProperties = ruler.readProperties(site);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomNavigateOptions customNavigateOptions = new ItemNavigateOptions(rulesProperties);
		PageNavigate pageNavigate = new PageNavigate(page, customNavigateOptions);
		Options options = customNavigateOptions.getOptions();
		pageNavigate.navigate(url);
		List<String> pages = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());
		ChapterScraped chapterScraped = new ChapterScraped(pages);
		
		page.close();
	//	close();
		return chapterScraped;
	}
}
