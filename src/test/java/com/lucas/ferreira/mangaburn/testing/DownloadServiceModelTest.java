package com.lucas.ferreira.mangaburn.testing;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaHostScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaYabuScraping;

public class DownloadServiceModelTest {
	private final static String TITLE_URL_TEST_MANGA_HOST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049";

	private final static String TITLE_URL_TEST_MANGA_YABU = "https://mangayabu.top/manga/boku-no-hero-academia/";

	private final static String TITLE_URL_TEST_ANITUBE = "https://www.anitube.site/872866/";

	private final static String TITLE_URL_TEST_GOYABU = "https://goyabu.com/assistir/golden-kamuy-3rd-season/";

	private final static String SEARCH = "One Piece";

	 @Test
	public void downloadMangaHost() {
		WebScraping webScraping = new MangaHostScraping();
		MangaWebData mangaWebData = new MangaWebData("One Piece MangaHost");
		mangaWebData.setUrl(TITLE_URL_TEST_MANGA_HOST);
		mangaWebData = (MangaWebData) webScraping.fecthTitle(mangaWebData);
		Collections collections = new MangaCollection();
		collections.setDestination("D:\\");
		ItemWebData item = webScraping.fecthItem(mangaWebData.getWebDatas().get(mangaWebData.getWebDatas().size() - 1));
		item.setName("Test simple " + 1);
		item.download(collections);

	}

	@Test
	public void downloadMangaYabu() {
		WebScraping webScraping = new MangaYabuScraping();
		MangaWebData mangaWebData = new MangaWebData("One Piece MangaYabu");
		mangaWebData.setUrl(TITLE_URL_TEST_MANGA_YABU);
		mangaWebData = (MangaWebData) webScraping.fecthTitle(mangaWebData);
		Collections collections = new MangaCollection();
		collections.setDestination("D:\\");
		ItemWebData item = webScraping.fecthItem(mangaWebData.getWebDatas().get(mangaWebData.getWebDatas().size() - 1));
		item.setName("Test simple " + 1);
		item.download(collections);

	}

	@Test
	public void downloadAnitube() {
		WebScraping webScraping = new AnitubeScraping();
		AnimeWebData animeWebData = new AnimeWebData("One Piece Anitube");
		animeWebData.setUrl(TITLE_URL_TEST_ANITUBE);
		animeWebData = (AnimeWebData) webScraping.fecthTitle(animeWebData);
		Collections collections = new MangaCollection();
		collections.setDestination("D:\\");
		ItemWebData item = webScraping.fecthItem(animeWebData.getWebDatas().get(animeWebData.getWebDatas().size() - 1));
		item.setName("Test simple " + 1);
		item.download(collections);

	}

	@Test
	public void downloadGoyabu() {
		WebScraping webScraping = new GoyabuScraping();
		AnimeWebData animeWebData = new AnimeWebData("One Piece Goyabu");
		animeWebData.setUrl(TITLE_URL_TEST_GOYABU);
		animeWebData = (AnimeWebData) webScraping.fecthTitle(animeWebData);
		Collections collections = new MangaCollection();
		collections.setDestination("D:\\");

		ItemWebData item = webScraping.fecthItem(animeWebData.getWebDatas().get(animeWebData.getWebDatas().size() - 1));
		item.setName("Test simple " + 1);
		item.download(collections);

	}

}
