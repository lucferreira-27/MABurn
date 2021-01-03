package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.WebScrapingUtil;

public class MangaHostScraping extends WebScraping {
	private MangaWebData mangaWebData;
	private Scraper scraper = new Scraper();
	private String responseBody;
	private Document document;

	public MangaHostScraping() {

	}

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub

		mangaWebData = (MangaWebData) titleWebData;
		mangaWebData.setSite(getSite());

		responseBody = ConnectionModel.connect(mangaWebData.getUrl());

		mangaWebData.setWebDatas(fetchChaptersUrl());

		return mangaWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub

		responseBody = ConnectionModel.connect(itemWebData.getUrl());

		ChapterWebData chapterWebData = (ChapterWebData) itemWebData;

		chapterWebData = fetchPagesUrl(chapterWebData);

		return chapterWebData;
	}

	@Override
	public List<SearchTitleWebData> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub
		String result = bingSearch(querry, getSite());
		if (!isTitlePage(result, "https://mangahosted.com/manga/")) {
			result = getTitlePage(result);
		} 
		SearchTitleWebData searchTitleWebData = new SearchTitleWebData(getSite());
		searchTitleWebData.setUrl(result);
		return Arrays.asList(searchTitleWebData);

	}

	public String getTitlePage(String url) {
		return url.substring(0, url.lastIndexOf("/"));
	}

	@Override
	protected boolean isTitlePage(String url, String expectedUrl) {
		// TODO Auto-generated method stub
		if (super.isTitlePage(url, expectedUrl)) {
			int bars = url.split("/").length;
			if (bars == 4)
				return true;
		}
		return false;

	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.MANGA_HOST;
	}

	private List<SearchTitleWebData> fetchAllItensOnTable(Document document) {
		// TODO Auto-generated method stub
		List<SearchTitleWebData> searchTitleWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(document, ".table.table-search.table-hover > tbody > tr > td > a");
		elements.forEach(element -> {

			SearchTitleWebData searchTitle = new SearchTitleWebData(getSite());
			searchTitle.setUrl(element.attr("href"));
			searchTitle.setName(element.attr("title"));
			searchTitleWebDatas.add(searchTitle);

		});
		return searchTitleWebDatas;
	}

	private List<ItemWebData> fetchChaptersUrl() {

		List<ItemWebData> chapterWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), ".btn-green.w-button.pull-left");
		elements.forEach(element -> {

			ChapterWebData chapterWebData = new ChapterWebData(mangaWebData);
			chapterWebData.setName(element.attr("title"));
			chapterWebData.setUrl(element.attr("href"));
			WebScrapingUtil.removeTrashFromStringChapter(chapterWebData, getSite());

			chapterWebDatas.add(chapterWebData);

		});
		return chapterWebDatas;

	}

	private ChapterWebData fetchPagesUrl(ChapterWebData chapterWebData) {

		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), "picture > img");
		elements.forEach(element -> {

			chapterWebData.addPagesUrl((element.attr("src")));
		});
		return chapterWebData;

	}

}
