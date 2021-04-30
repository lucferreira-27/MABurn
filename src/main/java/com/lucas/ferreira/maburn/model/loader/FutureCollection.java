package com.lucas.ferreira.maburn.model.loader;

import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.collections.ObservableList;

public class FutureCollection  {
	
	private final ObservableList<CollectionItem> futureItems;
	
	public FutureCollection(ObservableList<CollectionItem> futureItems) {
		// TODO Auto-generated constructor stub
		this.futureItems = futureItems;
	}
	
	public ObservableList<CollectionItem> getFutureItems() {
		return futureItems;
	}


}
