package com.lucas.ferreira.maburn.model.fetch;

import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class SaveData {
	private CollectionTitle title;
	private RegisteredSite registeredSite;
	private String url;

	public SaveData() {
	}

	public SaveData(CollectionTitle title, RegisteredSite registeredSite, String url) {
		this.title = title;
		this.registeredSite = registeredSite;
		this.url = url;
	}

	public RegisteredSite getRegisteredSite() {
		return registeredSite;
	}

	public void setRegisteredSite(RegisteredSite registeredSite) {
		this.registeredSite = registeredSite;
	}

	public CollectionTitle getTitle() {
		return title;
	}

	public void setTitle(CollectionTitle title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}
}
