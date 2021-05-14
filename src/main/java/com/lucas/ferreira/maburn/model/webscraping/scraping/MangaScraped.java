package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class MangaScraped implements TitleScraped {
	private String titleUrl;
	private Sites site;
	private List<String> chapters = new ArrayList<String>();

	public MangaScraped(String titleUrl, Sites site) {
		this.titleUrl = titleUrl;
		this.site = site;
	}

	@Override
	public Sites getSite() {
		return site;
	}
	@Override
	public String getTitleUrl() {
		return titleUrl;
	}
	
	@Override
	public List<String> getItemsScraped() {
		return chapters;
	}
}	
