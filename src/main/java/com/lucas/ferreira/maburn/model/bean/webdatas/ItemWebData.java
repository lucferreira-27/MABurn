package com.lucas.ferreira.maburn.model.bean.webdatas;

import com.lucas.ferreira.maburn.model.bean.GenericItem;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public interface ItemWebData extends GenericItem {
	public String getUrl();
	public void setName(String name);
	public Downloader<CollectionSubItem> getDownloader();
	public String getName();
	public Downloader<CollectionSubItem> download(Collections collections);
}
