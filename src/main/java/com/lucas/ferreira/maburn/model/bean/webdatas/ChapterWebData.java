package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.ArrayList;
import java.util.List;

public class ChapterWebData implements ItemWebData {
	private String url;
	private List<String> listPagesUrl = new ArrayList<>();
	
	
	public List<String> getListPagesUrl() {
		return listPagesUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void addPagesUrl(String url) {
		listPagesUrl.add(url);
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
