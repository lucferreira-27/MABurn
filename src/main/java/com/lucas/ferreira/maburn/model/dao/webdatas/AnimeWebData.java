package com.lucas.ferreira.maburn.model.dao.webdatas;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class AnimeWebData extends TitleWebData {
	private String name;
	private String url;
	private Sites site;
	private List<ItemWebData> webDatas = new ArrayList<ItemWebData>();

	public AnimeWebData(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public void setSite(Sites site) {
		this.site = site;
	}
	public Sites getSite() {
		return site;
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

	public List<ItemWebData> getWebDatas() {
		return webDatas;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addWebDatas(EpisodeWebData webData) {
		webDatas.add(webData);
	}

	@Override
	public String toString() {
		return name;
	}
	

}
