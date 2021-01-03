package com.lucas.ferreira.maburn.model.download.queue;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class Fetcher extends Task<ItemWebData> {

	private WebScraping scraping;
	private ItemWebData item;
	private StringProperty fetchState;

	public Fetcher(ItemWebData item, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		this.scraping = scraping;
		this.item = item;
	}

	@Override
	protected ItemWebData call() throws Exception {

		fetchState.set(item.getUrl());
		scraping.fecthItem(item);

		return item;
	}
}
