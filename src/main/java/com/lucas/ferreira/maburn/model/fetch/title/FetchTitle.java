package com.lucas.ferreira.maburn.model.fetch.title;

import java.nio.file.Path;
import java.util.Collections;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.microsoft.playwright.impl.Driver;

public class FetchTitle  {

	private static Path driver;
	
	public TitleScraped fetch(TitleScraping titleScraping, SiteValues siteValues) {
		
		if(driver == null) {
			driver = Driver.ensureDriverInstalled(PlaywrightSettings.getEnv());
		}
		
		TitleScraped titleScraped = titleScraping.scrapeTitle(siteValues);
		//
		return titleScraped;

	}
	
	public static Path getDriverPath() {
		return driver;
	}
	
}
