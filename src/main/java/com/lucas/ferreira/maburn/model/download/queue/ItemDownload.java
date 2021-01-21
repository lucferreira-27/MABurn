package com.lucas.ferreira.maburn.model.download.queue;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ItemDownload implements Callable<Void> {

	private Downloader<CollectionSubItem> downloader;
	private ItemWebData itemWebData;
	private Collections collections;

	public ItemDownload(ItemWebData itemWebData, Collections collections) {
		// TODO Auto-generated constructor stub
		this.itemWebData = itemWebData;
		this.collections = collections;
	}

	@Override
	public Void call() throws Exception {
		// TODO Auto-generated method stub
		download();
		return null;
	}

	private void download() {
		downloader = itemWebData.download(collections);
	}

	public Downloader<CollectionSubItem> getDownloader() {
		return downloader;
	}

}
