package com.lucas.ferreira.maburn.model.bean.webdatas;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public interface ItemWebData {
	public String getUrl();
	public void setName(String name);
	public String getName();
	public CollectionSubItem download(Collections collections);
}
