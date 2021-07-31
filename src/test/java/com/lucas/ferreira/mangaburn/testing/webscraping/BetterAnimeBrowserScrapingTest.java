package com.lucas.ferreira.mangaburn.testing.webscraping;

import java.util.Arrays;
import java.util.List;
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

public class BetterAnimeBrowserScrapingTest {
	
	private Playwright playwright;
	private MyBrowser myBrowser;


	@Test
	public void testTitleScraping() {
		TitleScraping titleScraping = new AnimeScraping(Sites.BETTER_ANIME);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://betteranime.net/anime/legendado/hetalia-world-stars");
		
	}
	@Test
	public void testItemScraping() {
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch().newContext();
		ItemScraping scraping = new EpisodeScraping(Sites.BETTER_ANIME, context);
		EpisodeScraped episodeScraped = (EpisodeScraped) scraping.scrapeItem("https://betteranime.net/anime/legendado/dragon-quest-dai-no-daibouken-2020/episodio-35");
		
	}
	
	@Test
	public void testListItemScraping() {
		
		myBrowser = new MyBrowser(true);
		
		ListItemScraping listItemScraping = new ListEpisodeScraping(Sites.BETTER_ANIME, myBrowser);
		List<ScrapingWork> urls = Arrays.asList(
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-01",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-02",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-03",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-04",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-05",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-06",
				"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-07"
		).stream().map(url -> new ScrapingWork(url)).collect(Collectors.toList());
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
