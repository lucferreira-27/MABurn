package com.lucas.ferreira.maburn.model.bean.webdatas;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public abstract class ItemWebData {

	protected boolean fetched;

	public abstract String getUrl();

	public abstract void setName(String name);

	public abstract Downloader<CollectionSubItem> getDownloader();

	public abstract String getName();

	public abstract Downloader<CollectionSubItem> download(Collections collections);

	public abstract Sites getSite();
	

	public boolean isFetched() {
		return fetched;
	}

	public void setFetched(boolean bool) {
		this.fetched = bool;
	}
}
