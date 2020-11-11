package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.webscraping.databases.WebScrapingDatas;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.MyAnimeListScraping;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.MyAnimeListSearchDatas;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.MyAnimeListTitleDatas;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.PagesTypes;

public class MyAnimeListScrapingTest {
	private MyAnimeListScraping malScraping;
	private MyAnimeListScraping malScrapingSearh;
	public static final String URL_TEST = "https://myanimelist.net/anime/40454/Dungeon_ni_Deai_wo_Motomeru_no_wa_Machigatteiru_Darou_ka_III";
	public static final String URL_SEARCH_TEST = "https://myanimelist.net/manga.php?q=Dragon+Ball+Super+BR&cat=manga";

	@Before
	public void setUp() {
		malScraping = new MyAnimeListScraping(URL_TEST, PagesTypes.TITLE_PAGE);
		malScrapingSearh = new MyAnimeListScraping(URL_SEARCH_TEST, PagesTypes.SEARCH_PAGE);
	}

	@Test
	public void fetchAllTitlePageTest() {
		WebScrapingDatas datas = malScraping.fetchAll();
		MyAnimeListTitleDatas titleDatas = (MyAnimeListTitleDatas) datas;
		
		titleDatas.getTitle();
		titleDatas.getImageSrc();

	}

	@Test
	public void fetchAllSearhPageTest() {
		WebScrapingDatas datas = malScrapingSearh.fetchAll();
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
		int result = searchDatas.getTitles().size();
		int expected = 50;
		assertThat(result, is(equalTo(expected)));

		result = searchDatas.getTitlesHyperLinks().size();
		expected = 50;
		assertThat(result, is(equalTo(expected)));
	}

	@Test
	public void fetchImagesTest() {
		malScraping.fetchImages();
		WebScrapingDatas datas = malScraping.getDatas();
		MyAnimeListTitleDatas titleDatas = (MyAnimeListTitleDatas) datas;
		String imageCoverExpected = "https://cdn.myanimelist.net/images/anime/1523/108380.jpg";
		assertThat(titleDatas.getImageSrc(), is(equalTo(imageCoverExpected)));
	}

	@Test
	public void fetchTextContentTest() {
		malScraping.fetchTextContent();
		WebScrapingDatas datas = malScraping.getDatas();
		String titleExpected = "Dungeon ni Deai wo Motomeru no wa Machigatteiru Darou ka III";
		MyAnimeListTitleDatas malDatas = (MyAnimeListTitleDatas) datas;
		assertThat((malDatas.getTitle()), is(equalTo(titleExpected)));

		String synopsisExpected = "Third season of Dungeon ni Deai wo Motomeru no wa Machigatteiru Darou ka.";
		assertThat(malDatas.getSynopsis(), is(equalTo(synopsisExpected)));

	}

	@Test
	public void fetchTextContentSearhPageTest() {
		WebScrapingDatas datas = malScrapingSearh.fetchTextContentSearhPage();
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
		int expected = 50;
		int result = searchDatas.getTitles().size();
		assertThat(expected, is(equalTo(result)));
	}

	// @Test
//	public void fetchImagesSearhPageTest() {
//		WebScrapingDatas datas = malScrapingSearh.fetchImagesSearchPage();
//		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
//		System.out.println(searchDatas.getImagesSrc());
//		int expected = 50;
//		int result = searchDatas.getTitles().size();
//		assertThat(expected, is(equalTo(result)));
//	}
	@Test
	public void fetchHyperLinksSearhPageTest() {
		WebScrapingDatas datas = malScrapingSearh.fetchHyperLinksSearhPage();
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
		int expected = 50;
		int result = searchDatas.getTitlesHyperLinks().size();
		assertThat(expected, is(equalTo(result)));
	}

}
