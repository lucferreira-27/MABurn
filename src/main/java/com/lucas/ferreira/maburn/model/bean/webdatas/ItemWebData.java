package com.lucas.ferreira.maburn.model.bean.webdatas;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;

public abstract class ItemWebData {
	
	//protected static ExecutorService exec = Executors.newFixedThreadPool(10);

	protected boolean fetched;

	public abstract String getUrl();

	public abstract void setName(String name);



	public abstract String getName();

	public abstract Downloader<CollectionSubItem> download(Collections collections, TitleDownload titleDownload);

	public abstract Sites getSite();
	

	public boolean isFetched() {
		return fetched;
	}

	public void setFetched(boolean bool) {
		this.fetched = bool;
	}
}
