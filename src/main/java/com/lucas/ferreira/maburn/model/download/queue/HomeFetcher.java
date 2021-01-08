package com.lucas.ferreira.maburn.model.download.queue;

import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

public class HomeFetcher extends Task<TitleWebData> {

	private WebScraping scraping;
	private TitleWebData title;
	private BooleanProperty fetchState = new SimpleBooleanProperty();

	public HomeFetcher(TitleWebData title, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		this.scraping = scraping;
		this.title = title;
	}

	@Override
	protected TitleWebData call() throws Exception {

		return fetch();
	}

	public TitleWebData fetch() {

		try {
			scraping.fecthTitle(title);
			fetchState.set(true);
			title.setFetched(true);
		} catch (Exception e) {
			// TODO: handle exception
			CustomLogger.log(e);
			title.setFetched(false);
			fetchState.set(true);
			throw e;

		}
		return title;
	}

}
