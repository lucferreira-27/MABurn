package com.lucas.ferreira.maburn.fetch;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class HomeFetcher implements Callable<Void> {

	private WebScraping scraping;
	private TitleWebData title;
	private ObjectProperty<TitleWebData> titlePropery = new SimpleObjectProperty<TitleWebData>();

	public HomeFetcher(TitleWebData title, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		System.out.println("HomeFetcher: " +  title.getWebDatas().size());
		this.scraping = scraping;
		this.title = title;
	}

	@Override
	public Void call() throws Exception {
		try {
			fetch();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void fetch() throws Exception {
		title.getWebDatas().clear();
		try {
			scraping.fecthTitle(title);
			title.setFetched(true);
		} catch (Exception e) {
			// TODO: handle exception
			CustomLogger.log(e);
			title.setFetched(false);

		}
		System.out.println(title.getWebDatas().size());
		titlePropery.set(title);

	}

	public ObjectProperty<TitleWebData> titlePropery() {
		return titlePropery;
	}

}
