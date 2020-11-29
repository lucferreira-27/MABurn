package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class MangaWebData implements TitleWebData {
	private String name;
	private Sites site;
	private String url;
	private List<ChapterWebData> webDatas = new ArrayList<ChapterWebData>();
	
	
	public MangaWebData(String name) {
		// TODO Auto-generated constructor stub
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

	public List<ChapterWebData> getWebDatas() {
		return webDatas;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setWebDatas(List<ChapterWebData> webDatas) {
		this.webDatas = webDatas;
	}
}
