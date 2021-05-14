package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class AnimeScraped implements TitleScraped{
		
	private String titleUrl;
	private Sites site;
	private List<String> episodes = new ArrayList<String>();
	
	public AnimeScraped(String titleUrl, Sites site) {
		this.titleUrl = titleUrl;
		this.site = site;
	}
	
	
	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	@Override
	public String getTitleUrl() {
		return titleUrl;
	}





	@Override
	public List<String> getItemsScraped() {
		// TODO Auto-generated method stub
		return episodes;
	}
}
