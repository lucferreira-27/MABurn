package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.connection.ScrapeEngine;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.SaikoScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class SaikoScrapingTest {
	private SaikoScraping scraping = new SaikoScraping();

	private final static String TITLE_URL_TEST1 = "https://saikoanimes.net/anime/kamisama-ni-natta-hi/";
	private final static String EPISODE_URL_TEST = "https://cloud.saikoanimes.net/G75";
	private final static String SEARCH = "Kamisama ni Natta Hi";
	private final static String SEARCH_ERROR = "JDAJDJIWJDIJWIDJIWDJ93-18HXZ";

	@Test
	public void fetchEpisodesAlternative() {
		try {
			ScrapeEngine engine = new ScrapeEngine("https://www.animestc.net/");

			HtmlAnchor div = (HtmlAnchor) engine.getPage().querySelectorAll(".episode-info-tabs-item-red").get(0);

			System.out.println("\n==============================================\n");
			Page page = engine.click(div);
			System.out
					.println( ((HtmlPage)page).asXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebScrapingException(e.getMessage());
		}
	}

	@Test
	public void fetchTitleTest() {
		AnimeWebData animeWebData = new AnimeWebData(SEARCH);
		animeWebData.setUrl(TITLE_URL_TEST1);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 12;
		animeWebData.getWebDatas().forEach(web -> CustomLogger.log(web.getName()));
		int result = animeWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));
	}

	@Test
	public void fetchItemTest() {

		EpisodeWebData episodeWebData = new EpisodeWebData(new AnimeWebData(SEARCH));
		episodeWebData.setUrl(EPISODE_URL_TEST);
		episodeWebData = (EpisodeWebData) scraping.fecthItem(episodeWebData);
		String result = episodeWebData.getDownloadLink();
		assertThat(true, is(result.contains("download_token")));

	}

	@Test
	public void fecthSearchTitle() {

		List<SearchResult> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		CustomLogger.log(searchTitleWebDatas.get(1).getUrl());
		int expect = 9;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
	
	@Test
	public void fetchInsideSearchFetchTitle() {
		List<SearchResult> results = scraping.insideSearchFetch(SEARCH);
		CustomLogger.logCollection(results);
	}

	@Test(expected = WebScrapingException.class)
	public void fecthSearchTitleError() {

		scraping.fetchSearchTitle(SEARCH_ERROR);

	}
}
