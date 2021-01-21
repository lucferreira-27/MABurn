package com.lucas.ferreira.mangaburn.testing.download;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.download.queue.HomeFetcher;
import com.lucas.ferreira.maburn.model.download.queue.ItemFetcher;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;

public class FetcherTest {
	private final static String TITLE_URL_TEST = "https://www.anitube.site/897019";
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";
	
	@Test
	public void homeFetcherTest() {
		AnimeWebData anime = new AnimeWebData("One Piece");
		anime.setUrl(TITLE_URL_TEST);

		HomeFetcher fetcher = new HomeFetcher(anime, new AnitubeScraping());
		try {
			fetcher.fetch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(anime.isFetched());
	}
	@Test
	public void itemFetcherTest() {
		EpisodeWebData episodeWebData = new EpisodeWebData(new AnimeWebData("One Piece"));
		episodeWebData.setUrl(EPISODE_URL_TEST);
		ItemFetcher fetcher = new ItemFetcher(episodeWebData, new AnitubeScraping());
		fetcher.fetch();
		assertTrue(episodeWebData.isFetched());
	}
	

}