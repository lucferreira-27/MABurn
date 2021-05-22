package com.lucas.ferreira.maburn.model.enums;

public enum SearchEngine {
	GOOGLE("https://www.google.com/search?q=", " site: "), BING(null, null);

	private String url;
	private String filter;

	private SearchEngine(String url, String filter) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public String getUrl() {
		return url;
	}

}
