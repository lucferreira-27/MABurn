package com.lucas.ferreira.maburn.model.enums;

public enum Sites {

	ANITUBE("https://www.anitube.site", Category.ANIME),
	GOYABU("https://goyabu.com", Category.ANIME),
	BETTER_ANIME("https://betteranime.net", Category.ANIME),
	MANGA_HOST("https://mangahosted.com", Category.MANGA),
	MANGA_YABU("https://mangayabu.top", Category.MANGA),
	MY_ANIMES_ONLINE("https://www.myanimesonline.biz", Category.ANIME),
	MANGA_OWL("https://mangaowl.net", Category.MANGA),
	GOGO_ANIME("https://www1.gogoanime.ai/", Category.ANIME);

	private String url;
	private Category category;

	private Sites(String url, Category category) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public Category getCategory() {
		return category;
	}
	
}	
