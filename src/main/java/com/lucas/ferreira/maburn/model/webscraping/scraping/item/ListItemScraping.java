package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.webscraping.browser.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.browser.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ListItemScraping {

	private MyBrowser myBrowser;
	private BooleanProperty scrapingDone = new SimpleBooleanProperty(false);

	public ListItemScraping(MyBrowser myBrowser) {
		this.myBrowser = myBrowser;

	}

	public ObservableList<ScrapingWork> scrapeItems(List<ScrapingWork> scrapingWorks) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		myBrowser.createBrowserPage(scrapingWorks.size());
		BrowserPage browserPage = myBrowser.getBrowsersPages().get(0);
		ObservableList<ScrapingWork> obsItems = FXCollections.observableArrayList();
		
		for (int i = 0; i < scrapingWorks.size(); i++) {
			ScrapeTask scrapeTask = new ScrapeTask(scrapingWorks.get(i), browserPage, this);
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
					
				} catch (InterruptedException e) {
					 
					e.printStackTrace();
				}
			}
			finish();
		}).start();
	}

	private void finish() {
		
		myBrowser.killAll();
		scrapingDone.set(true);

	}

	public abstract ItemScraped startItemScraping(String url,
			BrowserPage browserPage);

	public BooleanProperty isScrapingDone() {
		return scrapingDone;
	}

}
