package com.lucas.ferreira.maburn.model.update;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

public class Fetcher implements Callable<ItemWebData> {
	private ItemWebData itemWebData;
	private WebScraping scraping;

	public Fetcher(ItemWebData itemWebData, WebScraping webScraping) {

		this.itemWebData = itemWebData;
		this.scraping = webScraping;
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
		System.out.println("fetch?");
		try {
			System.out.println(itemWebData.getUrl());
			scraping.fecthItem(itemWebData);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("fetch!");
	}



}
