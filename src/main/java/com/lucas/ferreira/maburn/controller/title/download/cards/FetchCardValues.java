package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FetchCardValues {
	
	
	private String itemName;
	private String itemUrl;
	private ObjectProperty<FetchCardState> fetchCardState = new SimpleObjectProperty<FetchCardState>();

	
	public FetchCardValues(String itemName, String itemUrl) {
		this.itemName = itemName;
		this.itemUrl = itemUrl;
	}

	public String getItemName() {
		return itemName;
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
