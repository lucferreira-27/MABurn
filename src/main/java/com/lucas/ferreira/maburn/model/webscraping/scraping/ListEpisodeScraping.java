package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.List;
import java.util.Optional;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.BrowserPage;
import com.lucas.ferreira.maburn.model.webscraping.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.MyBrowser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListEpisodeScraping  implements ListItemScraping {

	private MyBrowser myBrowser;
	private BooleanProperty scrapingDone = new SimpleBooleanProperty(false);
	private Sites site;
	
	
	public ListEpisodeScraping(Sites site, MyBrowser myBrowser) {
		// TODO Auto-generated constructor stub
		this.myBrowser = myBrowser;
		this.site = site;
	}

	@Override
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

					new Thread(() -> {
						EpisodeScraping chapterScraping = new EpisodeScraping(site, browserPage.getContext());
						ItemScraped itemScraped = chapterScraping.scrapeItem(url);
						obsChapters.add(itemScraped);
						browserPage.setAvailable(true);
					}).start();
				});

				if (result.isEmpty()) {
					i--;
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

			}
			scrapingDone.set(true);


		}).start();
		return obsChapters;
	}
	public BooleanProperty isScrapingDone() {
		return scrapingDone;
	}

}
