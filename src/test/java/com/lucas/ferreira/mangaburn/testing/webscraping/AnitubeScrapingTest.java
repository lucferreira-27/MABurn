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
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class AnitubeScrapingTest {
	private final static String TITLE_URL_TEST = "https://www.anitube.site/897019";
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";
	private final static String SEARCH = "One Piece";
	private final static String SEARCH_ERROR = "JDAJDJIWJDIJWIDJIWDJ93-18HXZ";
	private final static String SEARCH_ERROR_2 = "Chibi Maruko-chan (1995)";

	
	
	private AnitubeScraping scraping = new AnitubeScraping();

	@Test
	public void fetchTitleTest() {
		AnimeWebData animeWebData = new AnimeWebData(SEARCH);
		animeWebData.setUrl(TITLE_URL_TEST);
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
		int result = episodeWebData.getAvaiblePlayersDefinitions().size();
		assertThat(expect, is(result));
	}
	@Test
	public void fecthSearchTitle() {

		List<SearchResult> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 1;
		int result = searchTitleWebDatas.size();
		assertThat(result, is(expect));

	}
	@Test
	public void getTitlePage() {
		String expect = TITLE_URL_TEST;

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
	public void fetchSearchTitleError() {

		scraping.fetchSearchTitle(SEARCH_ERROR);

	}
	@Test(expected = WebScrapingException.class)
	public void fecthSearchTitleErro2() {

		scraping.fetchSearchTitle(SEARCH_ERROR_2);

	}
}
