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

public class MangaYabuScraping extends WebScraping {
	private MangaWebData mangaWebData;
	private Scraper scraper = new Scraper();
	private String responseBody;
	private Document document;

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

		try {
			chapterWebData = fetchPagesUrl(chapterWebData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chapterWebData;
	}

	@Override
	public List<SearchTitleWebData> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub
		String result = bingSearch(querry, getSite());

		if (!isTitlePage(result, "https://mangayabu.top/manga/")) {
			result = getTitlePage(result);
		}
		SearchTitleWebData searchTitleWebData = new SearchTitleWebData(getSite());
		searchTitleWebData.setUrl(result);
		return Arrays.asList(searchTitleWebData);

	}

	public String getTitlePage(String url) {

		String responseBody = ConnectionModel.connect(url);
		Document document = Jsoup.parse(responseBody);

		Elements elements = scraper.scrapeSnippet(document, ".my-btn");
		return elements.get(2).attr("href");

	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.MANGA_YABU;
	}

	private List<ItemWebData> fetchChaptersUrl() {
		List<ItemWebData> chapterWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), ".single-chapter > a");
		elements.forEach(element -> {

			ChapterWebData chapterWebData = new ChapterWebData(mangaWebData);
			chapterWebData.setUrl(element.attr("href"));
			chapterWebData.setName(element.text());
			WebScrapingUtil.removeTrashFromStringChapter(chapterWebData, getSite());
			chapterWebDatas.add(chapterWebData);

		});
		return chapterWebDatas;

	}

	private ChapterWebData fetchPagesUrl(ChapterWebData chapterWebData) throws IOException {
		Elements elements;

		Document document = Jsoup.parse(responseBody);

		elements = scraper.scrapeSnippet(document, ".manga-pages > center > img");
		elements.forEach(element -> {

			chapterWebData.addPagesUrl((element.attr("src")));
		});
		return chapterWebData;

		// alignnone size-full wp-image
	}

	private Elements fetchPageUrlImgResponsive(Document document) {
		Elements elements = scraper.scrapeSnippet(document, ".img-responsive");
		return elements;
	}

	private Elements fetchPageUrlWpImage(Document document) {
		Elements elements = scraper.scrapeSnippet(document, ".manga-pages");
		return elements;
	}

	private List<SearchTitleWebData> fetchAllItensOnTable(Document document) {
		// TODO Auto-generated method stub
		List<SearchTitleWebData> searchTitleWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(document, ".feature > h2 > a");
		elements.forEach(element -> {

			SearchTitleWebData searchTitle = new SearchTitleWebData(getSite());
			searchTitle.setUrl(element.attr("href"));
			searchTitle.setName(element.text());
			searchTitleWebDatas.add(searchTitle);

		});
		return searchTitleWebDatas;
	}
}
