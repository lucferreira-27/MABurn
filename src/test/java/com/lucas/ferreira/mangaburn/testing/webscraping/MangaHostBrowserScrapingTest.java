package com.lucas.ferreira.mangaburn.testing.webscraping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MangaHostBrowserScrapingTest {
	
	private Playwright playwright;
	private MyBrowser myBrowser;
	
	
	@Before
	public void setup() {
		try {
			PlaywrightSettings.initConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testTitleScraping() {
		
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));

		TitleScraping titleScraping = new MangaScraping(Sites.MANGA_HOST);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://mangahost4.com/manga/boku-no-hero-academia-mh12781");
		
	}
	@Test
	public void testItemScraping() {
		System.out.println(PlaywrightSettings.getEnv());
		
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));
		BrowserContext context = playwright.firefox().launch().newContext();
		ItemScraping scraping = new ChapterScraping(Sites.MANGA_HOST,context);
		ItemScraped itemScraped = scraping.scrapeItem("https://mangahostz.com/manga/solo-leveling-mh24801/1");
		System.out.println(itemScraped);
		playwright.close();
	}
	
	@Test
	public void testListItemScraping() {
		
		myBrowser = new MyBrowser(true);
		
		ListItemScraping listItemScraping = new ListChapterScraping(Sites.MANGA_HOST, myBrowser);
		List<ScrapingWork> urls = Arrays.asList(
				"https://mangahostz.com/manga/solo-leveling-mh24801/1",
				"https://mangahostz.com/manga/solo-leveling-mh24801/2",
				"https://mangahostz.com/manga/solo-leveling-mh24801/3",
				"https://mangahostz.com/manga/solo-leveling-mh24801/4",
				"https://mangahostz.com/manga/solo-leveling-mh24801/5",
				"https://mangahostz.com/manga/solo-leveling-mh24801/6",
				"https://mangahostz.com/manga/solo-leveling-mh24801/8",
				"https://mangahostz.com/manga/solo-leveling-mh24801/9",
				"https://mangahostz.com/manga/solo-leveling-mh24801/10"
		).stream().map(url -> new ScrapingWork(url)).collect(Collectors.toList());;
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
