package com.lucas.ferreira.maburn.model.loader;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.bean.Chapter;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public interface CollectionLoader extends Callable<Collections> {
	public Collections loadCollection(String destination, Category category);

	public void loadAllItems();
	public void loadAllSubItems();
	public void loadItem(CollectionItem item);
	public void setDestination(String destination);

}
