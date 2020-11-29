package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaHostScraping;

public class MangaHostScrapingTest {
	private final static String TITLE_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049";
	private final static String CHAPTER_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049/1";
	private final static String SEARCH = "One Piece";

	private MangaHostScraping scraping = new MangaHostScraping();

	 @Test
	public void fetchTitleTest() {
		MangaWebData mangaWebData = new MangaWebData(SEARCH);
		mangaWebData.setUrl(TITLE_URL_TEST);
		mangaWebData = (MangaWebData) scraping.fecthTitle(mangaWebData);
		int expect = 0;
		int result = mangaWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));

	}

	// @Test
	public void fetchItemTest() {
		MangaWebData mangaWebData = new MangaWebData(SEARCH);

		ChapterWebData chapterWebData = new ChapterWebData(mangaWebData);
		chapterWebData.setUrl(CHAPTER_URL_TEST);
		chapterWebData = (ChapterWebData) scraping.fecthItem(chapterWebData);

		int expect = 57;
		int result = chapterWebData.getListPagesUrl().size();

		assertThat(expect, is(result));
	}

	@Test
	public void fecthSearchTitle() {

		List<SearchTitleWebData> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 10;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}

}
