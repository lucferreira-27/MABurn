package com.lucas.ferreira.maburn.model.enums;

import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaHostScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaYabuScraping;

public enum Sites {

	ANITUBE("https://www.anitube.site", Category.ANIME, new AnitubeScraping()),
	GOYABU("https://goyabu.com", Category.ANIME, new GoyabuScraping()),
	MANGA_HOST("https://mangahosted.com", Category.MANGA, new MangaHostScraping()),
	MANGA_YABU("https://mangayabu.top", Category.MANGA, new MangaYabuScraping());

	private String url;
	private Category category;
	private WebScraping scraping;

	private Sites(String url, Category category, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.category = category;
		this.scraping = scraping;
	}

	public String getUrl() {
		return url;
	}

	public Category getCategory() {
		return category;
	}
	
	public WebScraping getScraping() {
		return scraping;
	}
}	
