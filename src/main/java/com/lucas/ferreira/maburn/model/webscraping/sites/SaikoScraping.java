package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.connection.ScrapeEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.WebScrapingUtil;

public class SaikoScraping extends WebScraping {
	private Scraper scraper = new Scraper();

	private AnimeWebData animeWebData;
	private String responseBody;
	private Document document;


	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub
		animeWebData = (AnimeWebData) titleWebData;
		animeWebData.setSite(getSite());

		try {
			ScrapeEngine engine = new ScrapeEngine(animeWebData.getUrl(), null);
			document = Jsoup.parse(engine.getPage().asXml());

		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebScrapingException(e.getMessage());
		}

		// responseBody = ConnectionModel.connect(animeWebData.getUrl());
		animeWebData.getWebDatas().addAll(fetchEpisodes());

		return animeWebData;
	}

	private List<EpisodeWebData> fetchEpisodes() {
		// TODO Auto-generated method stub
		List<EpisodeWebData> episodeWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(document, "#51 .bnt-area > a");
		elements.forEach(element -> {

			EpisodeWebData episodeWebData = new EpisodeWebData(animeWebData);
			episodeWebData.setUrl(element.attr("href"));
			episodeWebData.setName(element.text());
			WebScrapingUtil.removeTrashFromStringEpisode(episodeWebData, getSite());
			episodeWebDatas.add(episodeWebData);

		});
		return episodeWebDatas;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub
		try {
			String location;

			ScrapeEngine engine = new ScrapeEngine(itemWebData.getUrl(), null);

			HtmlAnchor a = engine.getPage().getFirstByXPath("//a[@class='bnt-down']");

			engine.click(a);
			if (engine.getPage().getWebResponse().getStatusCode() == 302) {
				location = engine.getPage().getWebResponse().getResponseHeaderValue("Location");

			} else {
				engine.click(a);
				location = engine.getPage().getWebResponse().getResponseHeaderValue("Location");

			}
			EpisodeWebData episodeWebData = (EpisodeWebData) itemWebData;
			episodeWebData.setDownloadLink(location);
			episodeWebData.setPlayers(null);
			return episodeWebData;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SearchResult> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub
		try {
			String result = bingSearch(querry, getSite(), true);
			if (!isTitlePage(result, "https://saikoanimes.net/anime/")) {
				return insideSearchFetch(querry);
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
		String prefix = "/multimidia/?fwp_pesquisa=";
		String searchUrl = defaultUrl + prefix + querry;

		responseBody = ConnectionModel.connect(searchUrl);
		document = Jsoup.parse(responseBody);
		return fetchAllItensOnTable(document);
	}



	private List<SearchResult> fetchAllItensOnTable(Document document) {
		// TODO Auto-generated method stub
		try {
			List<SearchResult> searchTitleWebDatas = new ArrayList<>();
			Elements elements = scraper.scrapeSnippet(document, ".view-first> a");
			if (elements.stream().allMatch(el -> {
				return el.attr("href").isEmpty();
			})) {
				throw new WebScrapingException("Element don't found in querry");
			}
			elements.forEach(element -> {
				SearchResult searchTitle = new SearchResult(getSite());
				searchTitle.setName(element.select("div >div >.post-name").text());

				searchTitle.setUrl(element.attr("href"));
				searchTitleWebDatas.add(searchTitle);

			});
			return searchTitleWebDatas;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.SAIKO;
	}

}
