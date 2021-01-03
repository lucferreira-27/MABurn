package com.lucas.ferreira.maburn.model.webscraping;

import java.util.List;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.BingSearch;

public abstract class WebScraping {

	public abstract TitleWebData fecthTitle(TitleWebData titleWebData) throws WebScrapingException;

	public abstract ItemWebData fecthItem(ItemWebData itemWebData) throws WebScrapingException;

	public abstract List<SearchTitleWebData> fetchSearchTitle(String querry) throws WebScrapingException;

	protected String bingSearch(String querry, Sites site) {
		BingSearch bingSearch = new BingSearch(querry, site);
		return bingSearch.search();
	}

	protected boolean isTitlePage(String url ,String expectedUrl) {
		System.out.println(url);
		System.out.println(expectedUrl);
		return url.contains(expectedUrl);
	}

	public abstract Sites getSite();
}
