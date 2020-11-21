package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;

public class GoyabuScrapingTest {
	private GoyabuScraping scraping = new GoyabuScraping();
	private final static String TITLE_URL_TEST1 = "https://goyabu.com/assistir/golden-kamuy-3rd-season/";
	private final static String TITLE_URL_TEST2 = "https://goyabu.com/assistir/black-clover/";
	private final static String EPISODE_URL_TEST = "https://goyabu.com/videos/103023988/";
	private final static String SEARCH = "One Piece";

	@Test
	public void fetchTitleTestWithoutPages() {
		AnimeWebData animeWebData = new AnimeWebData();
		animeWebData.setUrl(TITLE_URL_TEST1);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 0;
		int result = animeWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));
	}

	@Test
	public void fetchTitleTestWithPages() {
		AnimeWebData animeWebData = new AnimeWebData();
		animeWebData.setUrl(TITLE_URL_TEST2);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 0;
		int result = animeWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));
	}

	@Test
	public void fetchItemTest() {
		EpisodeWebData episodeWebData = new EpisodeWebData();
		episodeWebData.setUrl(EPISODE_URL_TEST);
		episodeWebData = (EpisodeWebData) scraping.fecthItem(episodeWebData);
		int expect = 2;
		int result = episodeWebData.getAvaiblePlayersDefinitions().size();
		assertThat(expect, is(result));

	}

	@Test
	public void fecthSearchTitle() {

		List<SearchTitleWebData> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 3;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
}
