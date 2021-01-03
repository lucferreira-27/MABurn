package com.lucas.ferreira.maburn.model.download.queue;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.concurrent.Task;

public class Download extends Task<Downloader<CollectionSubItem>> {
	
	private ItemWebData itemWebData;
	private CollectionSubItem item;
	public Download(ItemWebData itemWebData, CollectionSubItem item) {
		// TODO Auto-generated constructor stub
		this.itemWebData = itemWebData;
		this.item = item;
	}
	
	@Override
	protected Downloader<CollectionSubItem> call() throws Exception {
		return itemWebData.download(item.getItem().getCollections());
	}

}
