package com.lucas.ferreira.mangaburn.testing.webscraping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
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
import com.microsoft.playwright.Playwright;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MangaYabuBrowserScrapingTest {
	
	private Playwright playwright;
	private MyBrowser myBrowser;

	
	
	@Test
	public void testTitleScraping() {
		playwright = Playwright.create();
		TitleScraping titleScraping = new MangaScraping(Sites.MANGA_YABU);
		TitleScraped titleScraped = titleScraping.scrapeTitle("https://mangayabu.top/manga/boku-no-hero-academia/");
		
		playwright.close();
	}
	@Test
	public void testItemScraping() {
		playwright = Playwright.create();

		BrowserContext context = playwright.firefox().launch().newContext();
		ItemScraping scraping = new ChapterScraping(Sites.MANGA_YABU,context);
		ItemScraped itemScraped = scraping.scrapeItem("https://mangayabu.top/ler/boku-no-hero-academia-capitulo-311-my614798/");
		
		playwright.close();

	}
	
	@Test
	public void testListItemScraping() {
		
		myBrowser = new MyBrowser(true);
		
		ListItemScraping listItemScraping = new ListChapterScraping(Sites.MANGA_YABU, myBrowser);
		List<ScrapingWork> urls = Arrays.asList(
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/",
				"https://mangayabu.top/ler/boku-no-hero-academia-capitulo-312-my618177/"
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
