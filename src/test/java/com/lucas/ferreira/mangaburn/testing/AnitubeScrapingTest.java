package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;

public class AnitubeScrapingTest {
	private final static String TITLE_URL_TEST = "https://www.anitube.site/897019/";
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";

	private AnitubeScraping scraping = new AnitubeScraping();

	@Test
	public void fetchTitleTest() {
		AnimeWebData animeWebData = new AnimeWebData();
		animeWebData.setUrl(TITLE_URL_TEST);
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

		String expect = "https://www.anitube.site/aHR0cHM6Ly9hbmltZXR1YmVicmFzaWwxLmJsb2dzcG90LmNvbS8yMDIwLzA5L2p1anV0c3Uta2Fpc2VuLWVwaXNvZGlvLTEuaHRtbA==/0/bg.mp4";
		String result = episodeWebData.getDirectDownloadUrl();
		assertThat(expect, is(result));
	}
}
