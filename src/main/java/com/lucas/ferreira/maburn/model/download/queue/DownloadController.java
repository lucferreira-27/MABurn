package com.lucas.ferreira.maburn.model.download.queue;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DownloadController {
	private TitleDownload titleDownload;
	private FetcherController fetcherController;

	public DownloadController(TitleDownload titleDownload, FetcherController fetcherController) {
		// TODO Auto-generated constructor stub
		this.titleDownload = titleDownload;
		this.fetcherController = fetcherController;

	}

	public void downloadAll() {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchAll();
		listenChange(obsItems);
	}

	public void downloadByIndex(int index) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchByIndex(index);
		listenChange(obsItems);
	}
	public void downloadBetween(int x, int y) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchBetween(x, y);
		listenChange(obsItems);
	}
	public void downloadUpdate(CollectionItem targetItem) {
		ObservableList<ItemWebData> obsItems = fetcherController.fetchUpdate(targetItem);
		listenChange(obsItems);
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
