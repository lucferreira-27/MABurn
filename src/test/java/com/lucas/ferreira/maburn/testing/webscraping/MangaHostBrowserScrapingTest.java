package com.lucas.ferreira.maburn.testing.webscraping;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RecoverSites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraping;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MangaHostBrowserScrapingTest {
	
	private Playwright playwright;
	private MyBrowser myBrowser;
	private LocalServer localServer = new LocalServer();

	
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
	public void testTitleScraping() throws Exception {
		
		if (LocalServer.getWebServer() == null) {
			WebServer webServer = localServer.create();
			webServer.start();
		}

		TitleScraping titleScraping = new AnimeScraping();
		SiteValues siteValues = new SiteValues();

		RecoverSites recoverSites = new RecoverSites();
		RegisteredSite registeredSite = recoverSites.recoverAll().stream()
				.filter((s) -> s.getSiteConfig().getName().equals("Manga Host")).findFirst().get();

		siteValues.setRegisteredSite(registeredSite);
		siteValues.setUrl("https://mangahost4.com/manga/boku-no-hero-academia-mh14663");
		TitleScraped titleScraped = titleScraping.scrapeTitle(siteValues);
		assertTrue(titleScraped.getItemsScraped().size() > 300);
		
	}
	@Test
	public void testItemScraping() throws Exception {
		if (LocalServer.getWebServer() == null) {
			WebServer webServer = localServer.create();
			webServer.start();
		}
		playwright = Playwright.create();
		BrowserContext context = playwright.firefox().launch().newContext();
		RecoverSites recoverSites = new RecoverSites();
		RegisteredSite registeredSite = recoverSites.recoverAll().stream()
				.filter((s) -> s.getSiteConfig().getName().equals("Manga Host")).findFirst().get();
		SiteValues siteValues = new SiteValues();

		siteValues.setRegisteredSite(registeredSite);
		siteValues.setTarget("Ler Capítulo 1");
		siteValues.setUrl("https://mangahost4.com/manga/one-piece-br-mh27284/1021");

		ItemScraping scraping = new ChapterScraping(registeredSite, context);

		ChapterScraped chapterScraped = (ChapterScraped) scraping.scrapeItem(siteValues);
		assertTrue(chapterScraped.getPagesLinks().size() > 10);
	}
	
	@Test
	public void testListItemScraping() throws Exception {
		if (LocalServer.getWebServer() == null) {
			WebServer webServer = localServer.create();
			webServer.start();
		}
		RecoverSites recoverSites = new RecoverSites();

		myBrowser = new MyBrowser(true);
		RegisteredSite registeredSite = recoverSites.recoverAll().stream()
				.filter((s) -> s.getSiteConfig().getName().equals("Manga Host")).findFirst().get();
		
		ListItemScraping listItemScraping = new ListChapterScraping(registeredSite, myBrowser);
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
		).stream().map(url -> {
			SiteValues siteValues = new SiteValues();
			siteValues.setRegisteredSite(registeredSite);
			siteValues.setTarget("Ler 01");
			siteValues.setUrl(url);
			return new ScrapingWork(siteValues);

		}).collect(Collectors.toList());
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
				            assertTrue(itemScraped.getSiteResult().getItemsValues().get(0).getUrls().size() > 0);

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
