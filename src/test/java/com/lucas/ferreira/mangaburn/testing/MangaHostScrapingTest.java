package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaHostScraping;

public class MangaHostScrapingTest {
	private final static String TITLE_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049";
	private final static String CHAPTER_URL_TEST = "https://mangahosted.com/manga/shingeki-no-kyojin-attack-on-titan-mh31049/1";

	private MangaHostScraping scraping = new MangaHostScraping();

	@Test
	public void fetchTitleTest() {
		MangaWebData mangaWebData = new MangaWebData();
		mangaWebData.setUrl(TITLE_URL_TEST);
		mangaWebData = (MangaWebData) scraping.fecthTitle(mangaWebData);
		int expect = 144;
		int result = mangaWebData.getWebDatas().size();
		
		assertThat(expect, is(result));

	}

	@Test
	public void fetchItemTest() {

		ChapterWebData chapterWebData = new ChapterWebData();
		chapterWebData.setUrl(CHAPTER_URL_TEST);
		chapterWebData = (ChapterWebData) scraping.fecthItem(chapterWebData);
		
		int expect = 57;
		int result = chapterWebData.getListPagesUrl().size();
		
		assertThat(expect, is(result));
	}

}
