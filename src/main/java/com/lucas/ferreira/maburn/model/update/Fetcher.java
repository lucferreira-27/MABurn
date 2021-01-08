package com.lucas.ferreira.maburn.model.update;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.property.StringProperty;

public class Fetcher implements Callable<ItemWebData> {
	private ItemWebData itemWebData;
	private WebScraping scraping;
	private StringProperty fetchState;
	
	public Fetcher(ItemWebData itemWebData, WebScraping webScraping, StringProperty fetchState) {

		this.itemWebData = itemWebData;
		this.scraping = webScraping;
		this.fetchState = fetchState;
	}
	
	
	
	@Override
	public ItemWebData call() throws Exception {
		// TODO Auto-generated method stub
		try {
		fetch();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return itemWebData;
	}

	private void fetch() {
		CustomLogger.log("fetch?");
		try {
			CustomLogger.log(itemWebData.getUrl());
			fetchState.set(itemWebData.getUrl());
			scraping.fecthItem(itemWebData);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		CustomLogger.log("fetch!");
	}
	public StringProperty getFetchState() {
		return fetchState;
	}



}
