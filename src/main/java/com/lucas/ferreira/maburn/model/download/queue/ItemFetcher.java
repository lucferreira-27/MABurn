package com.lucas.ferreira.maburn.model.download.queue;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ItemFetcher implements Callable<Void> {

	private WebScraping scraping;
	private ItemWebData item;
	private BooleanProperty fetchStatePropery = new SimpleBooleanProperty();

	public ItemFetcher(ItemWebData item, WebScraping scraping) {
		// TODO Auto-generated constructor stub
		this.scraping = scraping;
		this.item = item;
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

	public ItemWebData fetch() {

		try {
			scraping.fecthItem(item);
			
			
			item.setFetched(true);
			fetchStatePropery.set(true);
		} catch (Exception e) {
			// TODO: handle exception
			CustomLogger.log(e);
			item.setFetched(false);
			fetchStatePropery.set(true);
			throw e;
		}
		return item;
	}

	public BooleanProperty fetchStatePropery() {
		return fetchStatePropery;
	}

}
