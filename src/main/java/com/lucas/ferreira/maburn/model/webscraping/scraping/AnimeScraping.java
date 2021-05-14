package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.TimeoutError;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.AutoBrowser;
import com.lucas.ferreira.maburn.model.webscraping.CustomNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.ItemNavigateOptions;
import com.lucas.ferreira.maburn.model.webscraping.Options;
import com.lucas.ferreira.maburn.model.webscraping.PageNavigate;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.lucas.ferreira.maburn.model.webscraping.TitleNavigateOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.options.WaitUntilState;

public class AnimeScraping extends AutoBrowser implements TitleScraping {

	private Sites site;

	public AnimeScraping(Sites site, Playwright playwright) {
		// TODO Auto-generated constructor stub
		super(playwright);
		this.site = site;

	}

	public Sites getSite() {
		return site;
	}

	@Override
	public TitleScraped scrapeTitle(String url) {
		// TODO Auto-generated method stub
		launch(false);
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findTitleScript(site);
		ScrapingRuler ruler = new ScrapingRuler();
		RulesProperties rulesProperties = null;
		try {
			rulesProperties = ruler.readProperties(site);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Page page = newPage();
		CustomNavigateOptions customNavigateOptions = new TitleNavigateOptions(rulesProperties);
		PageNavigate pageNavigate = new PageNavigate(page, customNavigateOptions);
		Options options = customNavigateOptions.getOptions();
		
		pageNavigate.navigate(url);
		
		
		List<String> episodios = (ArrayList<String>) page.evaluate(script, options.getSelectQuery());

		AnimeScraped animeScraped = new AnimeScraped(url, site);
		animeScraped.getItemsScraped().addAll(episodios);
		close();
		return animeScraped;

	}

}
