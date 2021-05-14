package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.dao.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.BrowserBurn;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.WebScrapingUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BetterAnimeScraping extends WebScraping {
	private AnimeWebData animeWebData;
	private Page page;

	private Evaluate evaluate = new Evaluate();

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) throws WebScrapingException {
		// TODO Auto-generated method stub
		animeWebData = (AnimeWebData) titleWebData;
		animeWebData.setSite(getSite());
		page = newPage();
		gotoPage(animeWebData.getUrl());

		animeWebData.getWebDatas().addAll(fetchEpisodesUrl());
		page.close();
		return animeWebData;
	}

	private Page newPage() {
		System.out.println("BROWSER NEW CONTEXT");
		BrowserContext context = BrowserBurn.getBrowser().newContext();
		System.out.println("NEW PAGE");
		Page page = context.newPage();
		return page;
	}

	private void gotoPage(String url) {
		page.navigate(url);
	}

	private List<ItemWebData> fetchEpisodesUrl() {

		String script = evaluate.findTitleScript(getSite());

		List<ItemWebData> episodeWebDatas = new ArrayList<>();

		List<String> episodes = (ArrayList<String>) page.evaluate(script);

		for (int i = 0; i < episodes.size(); i++) {

			System.out.println(episodes.get(i));

			EpisodeWebData episodeWebData = new EpisodeWebData(animeWebData);

			String episode = episodes.get(i);
			WebScrapingUtil.renameElementToCustomName(i, episodeWebData);
			episodeWebData.setUrl(episode);
			episodeWebDatas.add(episodeWebData);
		}
		// TODO Auto-generated method stub

		return episodeWebDatas;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) throws WebScrapingException {
		// TODO Auto-generated method stub
		page = newPage();
		gotoPage(itemWebData.getUrl());

		String script = evaluate.findItemScript(getSite());

		String src = (String) page.evaluate(script);


		((EpisodeWebData) itemWebData).setDownloadLink(src);
		page.close();
		return itemWebData;

	}

	@Override
	public List<SearchResult> fetchSearchTitle(String querry) throws WebScrapingException {
		// TODO Auto-generated method stub
		try {
			String result = search(querry, getSite(), true);
			if (!isTitlePage(result, "https://betteranime.net/anime/")) {
				throw new Exception("Erro bad result");
				// return insideSearchFetch();
			} else {
				SearchResult searchTitleWebData = new SearchResult(getSite());
				searchTitleWebData.setUrl(result);
				return Arrays.asList(searchTitleWebData);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private String getTitlePage(String result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.BETTER_ANIME;
	}

}