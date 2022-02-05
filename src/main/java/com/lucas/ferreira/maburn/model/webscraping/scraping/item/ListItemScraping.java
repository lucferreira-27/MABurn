package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.navigate.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ListItemScraping {

	private MyBrowser myBrowser;
	private BooleanProperty scrapingDone = new SimpleBooleanProperty(false);
	public ListItemScraping(MyBrowser myBrowser)  {
		this.myBrowser = myBrowser;

	}

	public ObservableList<ScrapingWork> scrapeItems(List<ScrapingWork> scrapingWorks) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		myBrowser.createBrowserPage(scrapingWorks.size());
		BrowserPage browserPage = myBrowser.getBrowsersPages().get(0);
		ObservableList<ScrapingWork> obsItems = FXCollections.observableArrayList();

		for (ScrapingWork scrapingWork : scrapingWorks) {
			ScrapeTask scrapeTask = new ScrapeTask(scrapingWork, browserPage, this);
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

	public abstract ItemScraped startItemScraping(SiteValues siteValues,
			BrowserPage browserPage);

	public BooleanProperty isScrapingDone() {
		return scrapingDone;
	}

}