package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.FetchException;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.dao.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchResult;
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
	public List<SearchResult> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub
		String result = null;

		try {

			result = search(querry, getSite(), true);

			if (!isTitlePage(result, "https://mangahosted.com/manga/")) {
				SearchResult searchTitleWebData = new SearchResult(getSite());
				searchTitleWebData.setUrl(getTitlePage(result));
				return Arrays.asList(searchTitleWebData);
			} else {
				SearchResult searchTitleWebData = new SearchResult(getSite());
				searchTitleWebData.setUrl(result);
				return Arrays.asList(searchTitleWebData);
			}
			}catch (Exception e) {
				// TODO: handle exception
				return null;
			}

	}

	public List<SearchResult> insideSearchFetch(String querry) {

		String defaultUrl = getSite().getUrl();
		String prefix = "/find/";
		String searchUrl = defaultUrl + prefix + querry;

		responseBody = ConnectionModel.connect(searchUrl);

		document = Jsoup.parse(responseBody);
		
		return fetchAllItensOnTable(document);
	}

	public String getTitlePage(String url) {
		return url.substring(0, url.lastIndexOf("/"));
	}

	@Override
	protected boolean isTitlePage(String url, String expectedUrl) {
		// TODO Auto-generated method stub
		if (super.isTitlePage(url, expectedUrl)) {
			int bars = url.split("/").length;
			System.out.println("bars: " + bars);
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

	private List<SearchResult> fetchAllItensOnTable(Document document) {
		// TODO Auto-generated method stub
		
		List<SearchResult> searchTitleWebDatas = new ArrayList<>();
		
		Elements elements = scraper.scrapeSnippet(document, ".table.table-search.table-hover > tbody > tr > td > a");
		
		elements.forEach(element -> {

			SearchResult searchTitle = new SearchResult(getSite());
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
		
		if(chapterWebData.getListPagesUrl().isEmpty()) {
			throw new FetchException("No files found");
		}
		
		return chapterWebData;

	}

}
