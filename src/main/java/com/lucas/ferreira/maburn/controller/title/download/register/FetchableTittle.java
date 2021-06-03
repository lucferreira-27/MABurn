package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FetchableTittle {
	
	private CollectionTitle collectionTitle;
	private Sites sourceSelect; 
	private String titleUrl;
	
	public FetchableTittle(CollectionTitle collectionTitle, Sites sourceSelect) {
		this.collectionTitle = collectionTitle;
		this.sourceSelect = sourceSelect;
	}

	public CollectionTitle getCollectionTitle() {
		return collectionTitle;
	}

	public Sites getSourceSelect() {
		return sourceSelect;
	}

	public void setSourceSelect(Sites sourceSelect) {
		this.sourceSelect = sourceSelect;
	}
	
	public String getTitleUrl() {
		return titleUrl;
	}
	
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}


	
	
}
