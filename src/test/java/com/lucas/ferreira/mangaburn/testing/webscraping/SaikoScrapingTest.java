package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.SaikoScraping;

public class SaikoScrapingTest {
	private WebScraping scraping = new SaikoScraping();
	
	private final static String TITLE_URL_TEST1 = "https://saikoanimes.net/anime/kamisama-ni-natta-hi/";
	private final static String EPISODE_URL_TEST = "https://cloud.saikoanimes.net/G75";
	private final static String SEARCH = "Kamisama ni Natta Hi";

	@Test
	public void fetchTitleTest() {
		AnimeWebData animeWebData = new AnimeWebData(SEARCH);
		animeWebData.setUrl(TITLE_URL_TEST1);
		animeWebData = (AnimeWebData) scraping.fecthTitle(animeWebData);
		int expect = 12;
		animeWebData.getWebDatas().forEach(web -> System.out.println(web.getName()));
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

		List<SearchTitleWebData> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		System.out.println(searchTitleWebDatas.get(1).getUrl());
		int expect = 9;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
}
