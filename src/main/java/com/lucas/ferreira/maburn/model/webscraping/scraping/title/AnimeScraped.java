package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

public class AnimeScraped implements TitleScraped {

	private String titleUrl;
	private Sites site;
	private List<String> episodes = new ArrayList<String>();
	private PageInfo pageInfo;

	public AnimeScraped(String titleUrl, Sites site, PageInfo pageInfo, List<String> episodios) {
		this.titleUrl = titleUrl;
		this.site = site;
		this.pageInfo = pageInfo;
		this.episodes = episodios;
	}

	@Override
	public Sites getSite() {
		
		return site;
	}

	@Override
	public String getTitleUrl() {
		return titleUrl;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}
	@Override
	public List<String> getItemsScraped() {
		
		return episodes;
	}
}
