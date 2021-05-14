package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.AutoBrowser;
import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.CustomNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.ItemNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

import javafx.collections.ObservableList;

public class EpisodeScraping  implements ItemScraping {

	private Sites site;
	private BrowserContext context;

	public EpisodeScraping(Sites site, BrowserContext context) {
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
		
		String video = (String) page.evaluate(script, options.getSelectQuery());

		Map<Definition, String> links = new HashMap<Definition, String>();
		links.put(Definition.DEFINITION_1080, video);
		EpisodeScraped episodeScraped = new EpisodeScraped(links);

		page.close();
		return episodeScraped;
	}





}
