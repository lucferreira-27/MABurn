package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.List;
import java.util.Optional;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.MyBrowser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListEpisodeScraping  extends ListItemScraping {

	private Sites site;
	
	
	public ListEpisodeScraping(Sites site, MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		super(myBrowser);
		this.site = site;
	}




	@Override
	protected void startItemScraping(ObservableList<ItemScraped> obsItems, String url, BrowserPage browserPage) {
		new Thread(() -> {
			ItemScraping chapterScraping = new EpisodeScraping(site, browserPage.getContext());
			ItemScraped itemScraped = chapterScraping.scrapeItem(url);
			obsItems.add(itemScraped);
			browserPage.setAvailable(true);
		}).start();
		
	}

}
