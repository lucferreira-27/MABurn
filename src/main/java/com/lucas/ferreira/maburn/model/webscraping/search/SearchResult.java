package com.lucas.ferreira.maburn.model.webscraping.search;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class SearchResult {
	private Sites site;
	private String url;
	private String name;
	public SearchResult(Sites site) {
		// TODO Auto-generated constructor stub
		this.site = site;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Sites getSite() {
		return site;
	}

	@Override
	public String toString() {
		return "SearchTitleWebData [site=" + site + ", url=" + url + ", name=" + name + "]";
	}
	
}
