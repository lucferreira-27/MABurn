package com.lucas.ferreira.maburn.fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.exceptions.FetchException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FetcherOrchestrator {

	private TitleWebData titleWebData;
	private WebScraping scraping;
	private ObjectProperty<TitleWebData> titlePropery = new SimpleObjectProperty<TitleWebData>();
	private BooleanProperty fetchDonePropery = new SimpleBooleanProperty(false);
	private static final ExecutorService exec = Executors.newFixedThreadPool(10);
	private ObservableList<ItemWebData> obsItems;
	private BooleanProperty cancel = new SimpleBooleanProperty(false);

	public FetcherOrchestrator(TitleWebData titleWebData, WebScraping scraping) {
		this.titleWebData = titleWebData;
		this.scraping = scraping;

	}

	public void fetchHome() {
		System.out.println(titleWebData.getWebDatas().size());
		HomeFetcher homeFetcher = new HomeFetcher(titleWebData, scraping);
		homeFetcher.titlePropery().bindBidirectional(titlePropery);
		exec.submit(homeFetcher);
	}

	public ObservableList<ItemWebData> fetchAll() throws FetchException {
		if (titleWebData.getWebDatas().isEmpty())
			throw new FetchException("The title has no web data");
		List<ItemWebData> items = new ArrayList<ItemWebData>();
		obsItems = FXCollections.observableArrayList(items);
		for (ItemWebData itemWebData : titleWebData.getWebDatas()) {
			ItemFetcher fetcher = new ItemFetcher(itemWebData, scraping);

			fetcher.fetchStatePropery().addListener((obs, oldvalue, newvalue) -> {
				System.out.println(itemWebData.isFetched());
				obsItems.add(itemWebData);

			});

			exec.submit(fetcher);

		}
		return obsItems;

	}

	public ObservableList<ItemWebData> fetchByIndex(int index) throws FetchException {
		if (titleWebData.getWebDatas().isEmpty())
			throw new FetchException("The title has no web data");
		List<ItemWebData> items = new ArrayList<ItemWebData>();
		obsItems = FXCollections.observableArrayList(items);
		ItemWebData itemWebData = titleWebData.getWebDatas().get(index);
		ItemFetcher fetcher = new ItemFetcher(itemWebData, scraping);

		fetcher.fetchStatePropery().addListener((obs, oldvalue, newvalue) -> {
			System.out.println(itemWebData.isFetched());
			obsItems.add(itemWebData);

		});
		if (!cancel.get())
			exec.submit(fetcher);

		return obsItems;

	}

	public ObservableList<ItemWebData> fetchBetween(int x, int y) throws FetchException {
		if (titleWebData.getWebDatas().isEmpty())
			throw new FetchException("The title has no web data");

		List<ItemWebData> items = new ArrayList<ItemWebData>();

		obsItems = FXCollections.observableArrayList(items);

		for (int i = x; i < y; i++) {
			ItemWebData itemWebData = titleWebData.getWebDatas().get(i);
			ItemFetcher fetcher = new ItemFetcher(itemWebData, scraping);

			fetcher.fetchStatePropery().addListener((obs, oldvalue, newvalue) -> {
				System.out.println(itemWebData.isFetched());
				obsItems.add(itemWebData);

			});

			exec.submit(fetcher);

		}
		return obsItems;
	}

	public ObservableList<ItemWebData> fetchUpdate(CollectionItem targetItem) throws FetchException {
		if (titleWebData.getWebDatas().isEmpty())
			throw new FetchException("The title has no web data");

		List<ItemWebData> items = new ArrayList<ItemWebData>();

		obsItems = FXCollections.observableArrayList(items);

		DifferenceSynchrony differenceSynchrony = new DifferenceSynchrony(titleWebData.getWebDatas());
		List<ItemWebData> result = differenceSynchrony.synch(targetItem);
		System.out.println("DiffrenceSyncrony Result: " + result.size());
		for (ItemWebData itemWebData : result) {
			ItemFetcher fetcher = new ItemFetcher(itemWebData, scraping);
			
			fetcher.fetchStatePropery().addListener((obs, oldvalue, newvalue) -> {
				System.out.println(itemWebData.isFetched());
				obsItems.add(itemWebData);

			});
			
			exec.submit(fetcher);

		}
		return obsItems;
	}

	public void cancel() {
		cancel.set(true);

		exec.shutdownNow();

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

	public BooleanProperty fetchDonePropery() {
		return fetchDonePropery;
	}

	public ObjectProperty<TitleWebData> titlePropery() {
		return titlePropery;
	}

}
