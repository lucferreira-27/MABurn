package com.lucas.ferreira.maburn.model.dao.webdatas;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class MangaWebData extends TitleWebData {
	private String name;
	private Sites site;
	private String url;
	private List<ItemWebData> webDatas = new ArrayList<ItemWebData>();
	
	
	public MangaWebData(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	
	public Sites getSite() {
		return site;
	}
	public void setSite(Sites site) {
		this.site = site;
	}

	public List<ItemWebData> getWebDatas() {
		return webDatas;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setWebDatas(List<ItemWebData> webDatas) {
		this.webDatas = webDatas;
	}
}
