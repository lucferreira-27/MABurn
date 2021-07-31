package com.lucas.ferreira.mangaburn.testing.webscraping;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListEpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class AnitubeAnimeBrowserScrapingTest {
	private Playwright playwright;
	private MyBrowser myBrowser;


	
	
	@Test
	public void testTitleScraping() {
		TitleScraping titleScraping = new AnimeScraping(Sites.ANITUBE);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://www.anitube.site/902027/");
		
		
		System.exit(0);
	}
	@Test
	public void testItemScraping() {
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch().newContext();
		ItemScraping scraping = new EpisodeScraping(Sites.ANITUBE, context);
		EpisodeScraped episodeScraped = (EpisodeScraped) scraping.scrapeItem("https://www.anitube.site/902031/");
		
		playwright.close();
	}
	
	@Test
	public void testListItemScraping() {
		
		myBrowser = new MyBrowser(true);
		
		ListItemScraping listItemScraping = new ListEpisodeScraping(Sites.ANITUBE, myBrowser);
		List<ScrapingWork> urls = Arrays.asList(
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/",
				"https://www.anitube.site/902031/"
		).stream().map(url -> new ScrapingWork(url)).collect(Collectors.toList());
		ObservableList<ScrapingWork> obsItems = listItemScraping.scrapeItems(urls);
		
		testScrapingItemsAndWait(listItemScraping, urls);
		



	}
	
	private void testScrapingItemsAndWait(ListItemScraping listItemScraping, List<ScrapingWork> urls) {
		ObservableList<ScrapingWork> obsItems = listItemScraping.scrapeItems(urls);
		
		obsItems.addListener(new ListChangeListener<ScrapingWork>() {
	        @Override
	        public void onChanged(ListChangeListener.Change<? extends ScrapingWork> c) {
				c.next();
				ScrapingWork scrapingWork = c.getList().get(c.getFrom());
				scrapingWork.getPropertyScrapeState().addListener((obs, oldvalue, newvalue) -> {
					if (newvalue == ScrapeState.SUCCEED) {
						ItemScraped itemScraped;
						try {
							itemScraped = scrapingWork.getWorkResult();
				            

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	        }

	    });
		
		while(!listItemScraping.isScrapingDone().get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
