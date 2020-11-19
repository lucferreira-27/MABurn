package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;

public class ScrapingResult<T extends TitleWebData> {
	private T webData;

	public ScrapingResult(T webData) {
		// TODO Auto-generated constructor stub
		this.webData = webData;
	}

	public T getWebData() {
		return webData;
	}

}
