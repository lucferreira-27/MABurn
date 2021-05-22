package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.List;
import java.util.Optional;

import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.MyBrowser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ListItemScraping {

	
	private MyBrowser myBrowser;
	private BooleanProperty scrapingDone = new SimpleBooleanProperty(true);

	public ListItemScraping(MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		this.myBrowser = myBrowser;
		
	}
	public ObservableList<ItemScraped> scrapeItems(List<String> urls) {
		// TODO Auto-generated method stub

		myBrowser.createBrowserPage(urls.size());

		ObservableList<ItemScraped> obsChapters = FXCollections.observableArrayList();
		new Thread(() -> {

			for (int i = 0; i < urls.size(); i++) {
				String url = urls.get(i);
				Optional<BrowserPage> result = myBrowser.getBrowsersPages().stream()
						.filter(page -> page.isAvailable() && page.isAlive()).findAny();

				result.ifPresent((browserPage) -> {
					browserPage.setAvailable(false);

					startItemScraping(obsChapters, url, browserPage);
				});

				if (result.isEmpty()) {
					i--; // REDO INDEX 
					waitForAvailableNextBrowserPage(result);
				}

			}
			
			waitForAllBrowserPagesAvailable();
			finish();

		}).start();
		return obsChapters;
	}
	
	protected void waitForAllBrowserPagesAvailable() {
		while(!myBrowser.getBrowsersPages().stream().allMatch(page -> page.isAvailable() && page.isAlive())) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void waitForAvailableNextBrowserPage(Optional<BrowserPage> result) {
		while (result.isEmpty()) {
			try {
				Thread.sleep(1000);
				result = myBrowser.getBrowsersPages().stream()
						.filter(page -> page.isAvailable() && page.isAlive()).findAny();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected void finish() {
		System.out.println("FINISH");
		myBrowser.killAll();
		scrapingDone.set(false);

	}	
	protected abstract void startItemScraping(ObservableList<ItemScraped> obsItems, String url, BrowserPage browserPage);
	
	
	public BooleanProperty isScrapingDone() {
		return scrapingDone;
	}
	
}
