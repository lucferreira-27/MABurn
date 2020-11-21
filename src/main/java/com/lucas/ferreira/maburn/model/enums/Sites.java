package com.lucas.ferreira.maburn.model.enums;

public enum Sites {

	ANITUBE("https://www.anitube.site"), GOYABU("https://goyabu.com"), MANGA_HOST("https://mangahosted.com"), MANGA_YABU("https://mangayabu.top");

	private String url;

	private Sites(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
