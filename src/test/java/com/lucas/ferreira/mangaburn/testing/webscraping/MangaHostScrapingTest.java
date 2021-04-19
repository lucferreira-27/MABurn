package com.lucas.ferreira.mangaburn.testing.webscraping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.dao.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaHostScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class MangaHostScrapingTest {
	private final static String TITLE_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049";
	private final static String CHAPTER_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049/1";
	private final static String SEARCH = "One Piece";
	private final static String SEARCH_ERROR = "JDAJDJIWJDIJWIDJIWDJ93-18HXZ";

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
	public void getTitlePage() {
		String expect = TITLE_URL_TEST;

		String result = scraping.getTitlePage(CHAPTER_URL_TEST);

		assertThat(expect, is(result));

	}

	

	@Test
	public void fetchInsideSearchFetchTitle() {
		List<SearchResult> results = scraping.insideSearchFetch(SEARCH);
		
		

		CustomLogger.logCollection(results);
	}

	@Test
	public void fecthSearchTitle() {

		List<SearchResult> searchTitleWebDatas = scraping.fetchSearchTitle(SEARCH);
		int expect = 1;
		int result = searchTitleWebDatas.size();
		assertThat(expect, is(result));

	}

	@Test(expected = WebScrapingException.class)
	public void fecthSearchTitleError() {

		scraping.fetchSearchTitle(SEARCH_ERROR);

	}

}
