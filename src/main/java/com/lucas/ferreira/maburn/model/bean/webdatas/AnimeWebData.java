package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.ArrayList;
import java.util.List;

public class AnimeWebData implements TitleWebData {
	private String url;
	private List<EpisodeWebData> webDatas = new ArrayList<EpisodeWebData>();

	public String getUrl() {
		return url;
	}

	public List<EpisodeWebData> getWebDatas() {
		return webDatas;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addWebDatas(EpisodeWebData webData) {
		webDatas.add(webData);
	}

}
