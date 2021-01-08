package com.lucas.ferreira.maburn.model.download.queue;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

public class ItemFetcher extends Task<ItemWebData> {

	private WebScraping scraping;
	private ItemWebData item;
	private BooleanProperty fetchState = new SimpleBooleanProperty();

	public ItemFetcher(ItemWebData item, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		this.scraping = scraping;
		this.item = item;
	}

	@Override
	protected ItemWebData call() throws Exception {

		return fetch();
	}

	public ItemWebData fetch() {
		
		try {
			scraping.fecthItem(item);
			fetchState.set(true);
			item.setFetched(true);
		} catch (Exception e) {
			// TODO: handle exception
			CustomLogger.log(e);
			item.setFetched(false);
			fetchState.set(true);
			throw e;
		}
		return item;
	}
	public BooleanProperty getFetchState() {
		return fetchState;
	}
}
