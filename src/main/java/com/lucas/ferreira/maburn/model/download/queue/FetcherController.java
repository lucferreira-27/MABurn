package com.lucas.ferreira.maburn.model.download.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

public class FetcherController {

	private TitleWebData titleWebData;
	private CollectionItem item;
	private WebScraping scraping;
	private BooleanProperty fetchDone = new SimpleBooleanProperty();
	private static final ExecutorService exec = Executors.newFixedThreadPool(3);
	private ObservableList<ItemWebData> items;

	public FetcherController(TitleWebData titleWebData, WebScraping scraping, CollectionItem item) {
		// TODO Auto-generated constructor stub
		titleWebData.getWebDatas().clear();
		this.titleWebData = titleWebData;
		this.scraping = scraping;
		this.item = item;
		fetchDone.set(false);

		

	}

	public FetcherController() {
		// TODO Auto-generated constructor stub
	}

	public void fetchHome() {


		scraping.fecthTitle(titleWebData);

	}

	public void fetchAll() {

		titleWebData.getWebDatas().forEach(item -> {
			ItemFetcher fetcher = new ItemFetcher(item, scraping);

			fetcher.getFetchState().addListener((obs, oldvalue, newvalue) -> {
				if (newvalue) {
					items.add(item);
				}
			});

			exec.submit(fetcher);
			fetcher.fetch();
		});
		fetchDone.set(true);

	}

	public void fetchByIndex(int index) {
		ItemWebData item = titleWebData.getWebDatas().get(index);
		ItemFetcher fetcher = new ItemFetcher(item, scraping);
		fetcher.fetch();
		fetchDone.set(true);

	}

	public void fetchBetween(int x, int y) {
		for (int i = x; i < y; i++) {
			ItemWebData item = titleWebData.getWebDatas().get(i);
			ItemFetcher fetcher = new ItemFetcher(item, scraping);
			fetcher.fetch();
		}
		fetchDone.set(true);
	}

	// TODO
//	public void fetchFix() {
//
//	}

	public void fetchUpdate() {
		DifferenceSynchrony differenceSynchrony = new DifferenceSynchrony(titleWebData.getWebDatas());
		differenceSynchrony.synch(item).forEach(it -> {
			ItemFetcher fetcher = new ItemFetcher(it, scraping);
			fetcher.fetch();
		});
		fetchDone.set(true);

	}

	public WebScraping getWebScraping() {
		return scraping;
	}

	public void setWebScraping(WebScraping webScraping) {
		this.scraping = webScraping;
	}

	public void setTitleWebData(TitleWebData titleWebData) {
		this.titleWebData = titleWebData;
	}

	public TitleWebData getTitleWebData() {
		return titleWebData;
	}

}
