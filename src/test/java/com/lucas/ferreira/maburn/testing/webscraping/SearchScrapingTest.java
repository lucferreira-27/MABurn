package com.lucas.ferreira.maburn.testing.webscraping;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.scraping.SearchScraping;
import com.lucas.ferreira.maburn.model.webscraping.search.SearchScraped;

public class SearchScrapingTest {


	
	@Test
	public void testTitleScraping() {
		SearchScraping searchScraping = new SearchScraping("Naruto", SearchEngine.GOOGLE, Sites.GOYABU);
		SearchScraped scraped = searchScraping.search();
		
	}
}
