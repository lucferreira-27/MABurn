package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.ArrayList;
import java.util.List;

public class MangaWebData implements TitleWebData {
	private String url;
	private List<ChapterWebData> webDatas = new ArrayList<ChapterWebData>();

	public String getUrl() {
		return url;
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
