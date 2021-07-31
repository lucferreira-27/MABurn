package com.lucas.ferreira.maburn.testing.webscraping;

import static org.junit.Assert.assertTrue;

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
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class GoGoBrowserScrapingTest {
	private Playwright playwright;
	private MyBrowser myBrowser;

	@Test
	public void testTitleScraping() {
		TitleScraping titleScraping = new AnimeScraping(Sites.GOGO_ANIME);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://www1.gogoanime.ai/category/kingdom-3rd-season");
		
	}

	@Test
	public void testItemScraping() {
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false))
				.newContext();
		ItemScraping scraping = new EpisodeScraping(Sites.GOGO_ANIME, context);
		EpisodeScraped episodeScraped = (EpisodeScraped) scraping
				.scrapeItem("https://www1.gogoanime.ai/kingdom-3rd-season-episode-1");
		
		playwright.close();
	}

	@Test
	public void testItemScrapingBadScraping() {
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch().newContext();
		ItemScraping scraping = new EpisodeScraping(Sites.GOGO_ANIME, context);
		EpisodeScraped episodeScraped = (EpisodeScraped) scraping
				.scrapeItem("httpds://www1.gogoanime.ai/kingdom-3rd-season-episode-1");
		playwright.close();

		assertTrue(episodeScraped.getException() != null);
		episodeScraped.getException().printStackTrace();
		// 
	}

	@Test
	public void testListItemScraping() {

		myBrowser = new MyBrowser(true);

		ListItemScraping listItemScraping = new ListEpisodeScraping(Sites.GOGO_ANIME, myBrowser);
		List<ScrapingWork> urls = Arrays.asList("https://www1.gogoanime.ai/kingdom-3rd-season-episode-1",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-2",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-3",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-4",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-5",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-6",
				"https://www1.gogoanime.ai/kingdom-3rd-season-episode-7").stream().map(url -> new ScrapingWork(url)).collect(Collectors.toList());;
		testScrapingItemsAndWait(listItemScraping, urls);

//		Scanner scanner = new Scanner(System.in);
//		scanner.nextLine();

		// myBrowser.killAll(); // -> BUG HERE!

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

		while (!listItemScraping.isScrapingDone().get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
