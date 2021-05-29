package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

import javafx.scene.image.Image;

public class MangaScraped implements TitleScraped {
	private String titleUrl;
	private Sites site;
	private List<String> chapters = new ArrayList<String>();
	private PageInfo pageInfo;

	public MangaScraped(String titleUrl, Sites site, PageInfo pageInfo, List<String> chapters) {
		this.titleUrl = titleUrl;
		this.site = site;
		this.pageInfo = pageInfo;
		this.chapters = chapters;
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
		return chapters;
	}


	
}
