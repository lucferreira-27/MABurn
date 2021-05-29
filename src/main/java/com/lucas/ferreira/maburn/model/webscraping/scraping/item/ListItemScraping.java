package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.webscraping.browser.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.browser.MyBrowser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ListItemScraping {

	private MyBrowser myBrowser;
	private BooleanProperty scrapingDone = new SimpleBooleanProperty(false);

	public ListItemScraping(MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		this.myBrowser = myBrowser;
		
		
	}

	public ObservableList<ItemScraped> scrapeItems(List<String> urls) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();

		myBrowser.createBrowserPage(urls.size());
		BrowserPage browserPage = myBrowser.getBrowsersPages().get(0);
		ObservableList<ItemScraped> obsItems = FXCollections.observableArrayList();

		for (int i = 0; i < urls.size(); i++) {
			String url = urls.get(i);
			ScrapeTask scrapeTask = new ScrapeTask(url, obsItems, browserPage, this);
			executorService.submit(scrapeTask);
	
		}
		executorService.shutdown();
		alertWhenScrapeFinish(executorService);

		return obsItems;
	}

	public void alertWhenScrapeFinish(ExecutorService executorService) {
		new Thread(() -> {
			while (!executorService.isTerminated()) {
				try {
					Thread.sleep(500);
					System.out.println("Scraping ...");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			finish();
		}).start();
	}

	private void finish() {
		System.out.println("FINISH");
		myBrowser.killAll();
		scrapingDone.set(true);

	}

	public abstract ItemScraped startItemScraping(ObservableList<ItemScraped> obsItems, String url,
			BrowserPage browserPage);

	public BooleanProperty isScrapingDone() {
		return scrapingDone;
	}

}
