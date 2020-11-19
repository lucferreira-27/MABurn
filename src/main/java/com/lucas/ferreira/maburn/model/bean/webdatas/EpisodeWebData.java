package com.lucas.ferreira.maburn.model.bean.webdatas;

public class EpisodeWebData implements ItemWebData {
	private String url;
	private String directDownloadUrl;

	public String getDirectDownloadUrl() {
		return directDownloadUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setDirectDownloadUrl(String directDownloadUrl) {
		this.directDownloadUrl = directDownloadUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
