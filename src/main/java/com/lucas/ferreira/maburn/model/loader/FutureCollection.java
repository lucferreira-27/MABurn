package com.lucas.ferreira.maburn.model.loader;

import com.lucas.ferreira.maburn.model.items.CollectionTitle;

import javafx.collections.ObservableList;

public class FutureCollection  {
	
	private final ObservableList<CollectionTitle> futureItems;
	
	public FutureCollection(ObservableList<CollectionTitle> futureItems) {
		// TODO Auto-generated constructor stub
		this.futureItems = futureItems;
	}
	
	public ObservableList<CollectionTitle> getFutureItems() {
		return futureItems;
	}


}
