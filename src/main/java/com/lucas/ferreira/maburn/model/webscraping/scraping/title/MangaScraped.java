package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

public class MangaScraped implements TitleScraped {
	private String titleUrl;
	private RegisteredSite registeredSite;
	private Map<String, List<String>> chapters = new LinkedHashMap<String, List<String>>();
	private PageInfo pageInfo;

	public MangaScraped(SiteResult siteResult) {
		this.titleUrl = siteResult.getPageInfo().getUrl();
		this.registeredSite = siteResult.getPageInfo().getRegisteredSite();
		this.pageInfo = siteResult.getPageInfo();
		siteResult.getItemsValues().forEach((value -> {
			chapters.put(value.getName(), value.getUrls());
		}));
	}



	@Override
	public String getTitleUrl() {
		return titleUrl;
	}

	@Override
	public RegisteredSite getRegisteredSite() {
		return registeredSite;
	}



	@Override
	public PageInfo getPageInfo() {
		return pageInfo;
	}



	@Override
	public Map<String, List<String>> getItemsScraped() {
		return chapters;
	}


	
}
