package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class GoyabuScrapingTest {
	private GoyabuScraping scraping = new GoyabuScraping();
	private final static String TITLE_URL_TEST1 = "https://goyabu.com/assistir/golden-kamuy-3rd-season/";
	private final static String TITLE_URL_TEST2 = "https://goyabu.com/assistir/black-clover/";
	private final static String EPISODE_URL_TEST = "https://goyabu.com/videos/103023988/";
	private final static String SEARCH = "One Piece";
	private final static String SEARCH_ERROR = "JDAJDJIWJDIJWIDJIWDJ93-18HXZ";


	@Test
	public void fetchTitleTestWithoutPages() {
		AnimeWebData animeWebData = new AnimeWebData(SEARCH);
		animeWebData.setUrl(TITLE_URL_TEST1);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 0;
		int result = animeWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));
	}

	@Test
	public void fetchTitleTestWithPages() {
		AnimeWebData animeWebData = new AnimeWebData(SEARCH);
		animeWebData.setUrl(TITLE_URL_TEST2);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 0;
		int result = animeWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));
	}

	@Test
	public void fetchItemTest() {
		EpisodeWebData episodeWebData = new EpisodeWebData(new AnimeWebData(SEARCH));
		episodeWebData.setUrl(EPISODE_URL_TEST);
		episodeWebData = (EpisodeWebData) scraping.fecthItem(episodeWebData);
		int expect = 1;
		System.out.println(episodeWebData.getAvaiblePlayersDefinitions());
		int result = episodeWebData.getAvaiblePlayersDefinitions().size();
		assertThat(expect, is(result));

	}

	@Test
	public void fecthSearchTitle() {

		List<SearchResult> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 3;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
	@Test
	public void getTitlePage() {
		String expect = TITLE_URL_TEST1;

		String result = scraping.getTitlePage(EPISODE_URL_TEST);
		CustomLogger.log(expect);
		CustomLogger.log(result);
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
