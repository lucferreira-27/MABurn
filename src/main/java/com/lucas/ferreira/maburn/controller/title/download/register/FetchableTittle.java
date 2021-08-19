package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class FetchableTittle {
	
	private CollectionTitle collectionTitle;
	private RegisteredSite sourceSelect; 
	private String titleUrl;
	
	public FetchableTittle(CollectionTitle collectionTitle, RegisteredSite sourceSelect) {
		this.collectionTitle = collectionTitle;
		this.sourceSelect = sourceSelect;
	}

	public CollectionTitle getCollectionTitle() {
		return collectionTitle;
	}

	public RegisteredSite getSourceSelect() {
		return sourceSelect;
	}

	public void setSourceSelect(RegisteredSite sourceSelect) {
		this.sourceSelect = sourceSelect;
	}
	
	public String getTitleUrl() {
		return titleUrl;
	}
	
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}


	
	
}
