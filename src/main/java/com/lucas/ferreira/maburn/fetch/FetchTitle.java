package com.lucas.ferreira.maburn.fetch;

import java.nio.file.Path;
import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.scraping.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.TitleScraping;
import com.microsoft.playwright.impl.Driver;

public class FetchTitle  {

	private static Path driver;
	
	public TitleScraped fetch(TitleScraping titleScraping, String url) {
		
		if(driver == null) {
			driver = Driver.ensureDriverInstalled();
		}
		
		TitleScraped titleScraped = titleScraping.scrapeTitle(url);
		//System.out.println(titleScraped.getItemsScraped());
		return titleScraped;

	}
	
	public static Path getDriverPath() {
		return driver;
	}
	
}
