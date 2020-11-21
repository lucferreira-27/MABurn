package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaYabuScraping;

public class MangaYabuScrapingTest {
	private final static String TITLE_URL_TEST = "https://mangayabu.top/manga/one-piece/";
	private final static String CHAPTER_URL_TEST = "https://mangayabu.top/ler/one-piece-capitulo-996-my432182/";
	private final static String SEARCH = "One Piece";

	private MangaYabuScraping scraping = new MangaYabuScraping();

	@Test
	public void fetchTitleTest() {
		MangaWebData mangaWebData = new MangaWebData();
		mangaWebData.setUrl(TITLE_URL_TEST);
		mangaWebData = (MangaWebData) scraping.fecthTitle(mangaWebData);
		int expect = 0;
		int result = mangaWebData.getWebDatas().size();

		assertNotEquals(expect, is(result));

	}

	@Test
	public void fetchItemTest() {

		ChapterWebData chapterWebData = new ChapterWebData();
		chapterWebData.setUrl(CHAPTER_URL_TEST);
		chapterWebData = (ChapterWebData) scraping.fecthItem(chapterWebData);

		int expect = 17;
		int result = chapterWebData.getListPagesUrl().size();

		assertThat(expect, is(result));
	}

	@Test
	public void fecthSearchTitle() {

		List<SearchTitleWebData> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 4;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}
}
