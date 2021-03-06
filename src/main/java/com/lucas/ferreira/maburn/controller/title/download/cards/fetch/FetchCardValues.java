package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.model.items.CollectionTitle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FetchCardValues {
	
	
	private String itemName;
	private String itemUrl;
	private CollectionTitle collectionTitle;
	private ObjectProperty<FetchCardState> fetchCardState = new SimpleObjectProperty<FetchCardState>(FetchCardState.IN_QUEUE);

	
	public FetchCardValues(CollectionTitle collectionTitle) {

		this.collectionTitle = collectionTitle;
	}
	
	public String getItemName() {
		return itemName;
	}
	public CollectionTitle getCollectionTitle() {
		return collectionTitle;
	}
	public void setCollectionTitle(CollectionTitle collectionTitle) {
		this.collectionTitle = collectionTitle;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public ObjectProperty<FetchCardState> getFetchCardState() {
		return fetchCardState;
	}
	
	
}
