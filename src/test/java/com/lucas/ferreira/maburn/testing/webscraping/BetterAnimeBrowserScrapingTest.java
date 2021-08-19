package com.lucas.ferreira.maburn.testing.webscraping;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RecoverSites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
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
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class BetterAnimeBrowserScrapingTest {

	private Playwright playwright;
	private MyBrowser myBrowser;
	private LocalServer localServer = new LocalServer();

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
				.filter((s) -> s.getSiteConfig().getName().equals("Better Animes")).findFirst().get();

		siteValues.setRegisteredSite(registeredSite);
		siteValues.setUrl("https://betteranime.net/anime/legendado/hetalia-world-stars");
		TitleScraped titleScraped = titleScraping.scrapeTitle(siteValues);
		assertTrue(titleScraped.getItemsScraped().size() == 12);
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
				.filter((s) -> s.getSiteConfig().getName().equals("Better Animes")).findFirst().get();
		SiteValues siteValues = new SiteValues();

		siteValues.setRegisteredSite(registeredSite);
		siteValues.setTarget("Episode 01");
		siteValues.setUrl("https://betteranime.net/anime/legendado/dragon-quest-dai-no-daibouken-2020/episodio-35");

		ItemScraping scraping = new EpisodeScraping(registeredSite, context);

		EpisodeScraped episodeScraped = (EpisodeScraped) scraping.scrapeItem(siteValues);
		assertNotNull(episodeScraped);
		assertTrue(episodeScraped.getVideoLinks().size() == 1);
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
				.filter((s) -> s.getSiteConfig().getName().equals("Better Animes")).findFirst().get();

		ListItemScraping listItemScraping = new ListEpisodeScraping(registeredSite, myBrowser);
		List<ScrapingWork> urls = Arrays
				.asList("https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-01",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-02",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-03",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-04",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-05",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-06",
						"https://betteranime.net/anime/legendado/hetalia-world-stars/episodio-07")
				.stream().map(url -> {
					SiteValues siteValues = new SiteValues();
					siteValues.setRegisteredSite(registeredSite);
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
				            assertTrue(itemScraped.getSiteResult().getItemsValues().get(0).getUrls().size() == 1);

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
