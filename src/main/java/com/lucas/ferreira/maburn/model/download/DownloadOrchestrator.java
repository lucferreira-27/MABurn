package com.lucas.ferreira.maburn.model.download;

import com.lucas.ferreira.maburn.fetch.FetcherOrchestrator;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DownloadOrchestrator {
	private TitleDownload titleDownload;
	private FetcherOrchestrator fetcherController;

	public DownloadOrchestrator(TitleDownload titleDownload, FetcherOrchestrator fetcherController) {
		// TODO Auto-generated constructor stub
		this.titleDownload = titleDownload;
		this.fetcherController = fetcherController;

	}

	public void downloadAll() {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchAll();
		titleDownload.setState(DownloadState.DOWNLOADING);
		Platform.runLater(() ->listenChange(obsItems));
	}

	public void downloadByIndex(int index) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchByIndex(index);
		titleDownload.setState(DownloadState.DOWNLOADING);

		Platform.runLater(() ->listenChange(obsItems));
	}
	public void downloadBetween(int x, int y) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchBetween(x, y);
		titleDownload.setState(DownloadState.DOWNLOADING);

		Platform.runLater(() ->listenChange(obsItems));
	}
	public void downloadUpdate(CollectionItem targetItem) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchUpdate(targetItem);
		titleDownload.setState(DownloadState.DOWNLOADING);
		Platform.runLater(() ->listenChange(obsItems));
	}


	private void listenChange(ObservableList<ItemWebData> obsItems) {
		obsItems.addListener(new ListChangeListener<ItemWebData>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends ItemWebData> c) {
				if (c.next()) {
					titleDownload.addItem(c.getList().get(c.getFrom()));
				}

			}

		});
	}
}
