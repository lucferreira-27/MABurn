package com.lucas.ferreira.maburn.testing.webscraping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MangaOwlBrowserScrapingTest {
	
	private Playwright playwright;
	private MyBrowser myBrowser;
	

	
	
	@Test
	public void testTitleScraping() {
		
		playwright = Playwright.create();

		TitleScraping titleScraping = new MangaScraping(Sites.MANGA_OWL);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://mangaowl.net/single/67387/shadows-house---digital-colored-comics?__cf_chl_jschl_tk__=804af9235017e1984bec339f6080d19b87366e98-1621202169-0-AZizIwKAM1JeKtg6i8-IbWy2F5v46NE8HAWE3sc41d0d12YgxbizCrGcVfQFXGzTHyAuTT0XYvoIfvKNUiv7l8T4QnIkb1eRoPGBlnNJFiZ9WRieoWirZhvu5rbLmWbmlJbVo9Dt0XR5JCEnrg-dk_EFFI_4DmoXcF5yKq6tLEKNPQScH-CSfB2KR35Y4QkaMx5sLibZ4PdcAJA4IAdiihlN-Fh6g2PMpYtiSshfJ94itq7y3gRd0O7de5SzyE_PjJ85ge8qjkwJnmmdT8H61rMl5QblvlIB5Z06paw8FY33IWdX8EqnNT0z-75ut9fpcyHITCV4DaGR6WYxteSCSc1-gbiDal7OWg_kS2Xm7UsymST0Ep_vgw1lifxbn5RRr-GwROEOsUWktywnmR5HOJPlVS7i7JZaKyguzhjdDjv1-9XS8_4eaeuz7JBqKTgj2pHjEB2daO6SkSEFXyR_eCFlWsXZyz933cYAztrjtCJyAckjkfbTVMIOpgJUBoyMn058xeolSr9WlkNGeoprV7w");
		
	}
	@Test
	public void testItemScraping() {
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true)).newContext();
		ItemScraping scraping = new ChapterScraping(Sites.MANGA_OWL,context);
		ItemScraped itemScraped = scraping.scrapeItem("https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D");
		
		playwright.close();
	}
	
	@Test
	public void testListItemScraping() {
		
		myBrowser = new MyBrowser(true);
		
		ListItemScraping listItemScraping = new ListChapterScraping(Sites.MANGA_OWL, myBrowser);
		List<ScrapingWork> urls = Arrays.asList(
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D",
				"https://chessmoba.us/reader/reader/67387/1193604/0?s=aHR0cHM6Ly9tYW5nYW93bC5uZXQ%3D"
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
