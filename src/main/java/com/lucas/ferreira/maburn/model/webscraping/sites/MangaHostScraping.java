package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

public class MangaHostScraping implements WebScraping {
	private MangaWebData mangaWebData;
	private Scraper scraper = new Scraper();
	private Response response;
	private Document document;

	public MangaHostScraping() {

	}

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub

		mangaWebData = (MangaWebData) titleWebData;
		mangaWebData.setSite(getSite());

		response = ConnectionModel.connect(mangaWebData.getUrl());

		mangaWebData.setWebDatas(fetchChaptersUrl());

		return mangaWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub

		response = ConnectionModel.connect(itemWebData.getUrl());

		ChapterWebData chapterWebData = (ChapterWebData) itemWebData;

		chapterWebData = fetchPagesUrl(chapterWebData);

		return chapterWebData;
	}

	@Override
	public List<SearchTitleWebData> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub

		String defaultUrl = getSite().getUrl();
		String prefix = "/find/";
		String searchUrl = defaultUrl + prefix + querry;

		response = ConnectionModel.connect(searchUrl);
		try {
			document = response.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fetchAllItensOnTable(document);

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
		try {
			List<ItemWebData> chapterWebDatas = new ArrayList<>();
			Elements elements = scraper.scrapeSnippet(response.parse(), ".btn-green.w-button.pull-left");
			elements.forEach(element -> {

				ChapterWebData chapterWebData = new ChapterWebData(mangaWebData);
				chapterWebData.setName(element.attr("title"));
				chapterWebData.setUrl(element.attr("href"));
				WebScrapingUtil.removeTrashFromStringChapter(chapterWebData, getSite());

				chapterWebDatas.add(chapterWebData);

			});
			return chapterWebDatas;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private ChapterWebData fetchPagesUrl(ChapterWebData chapterWebData) {

		try {
			Elements elements = scraper.scrapeSnippet(response.parse(), "picture > img");
			elements.forEach(element -> {

				chapterWebData.addPagesUrl((element.attr("src")));
			});
			return chapterWebData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
