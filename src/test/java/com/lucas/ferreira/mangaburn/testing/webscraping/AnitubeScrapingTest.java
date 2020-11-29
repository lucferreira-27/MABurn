package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;

public class AnitubeScrapingTest {
	private final static String TITLE_URL_TEST = "https://www.anitube.site/897019/";
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";
	private final static String SEARCH = "One Piece";

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

		List<SearchTitleWebData> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 3;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
}
