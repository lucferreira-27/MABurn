package com.lucas.ferreira.maburn.model.bean.webdatas;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class SearchTitleWebData {
	private Sites site;
	private String url;
	private String name;
	public SearchTitleWebData(Sites site) {
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
}
