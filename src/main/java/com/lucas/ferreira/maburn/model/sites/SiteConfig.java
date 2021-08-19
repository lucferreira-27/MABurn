package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.model.enums.Category;

public class SiteConfig {
	private String name;
	private String homeUrl;
	private String scriptPath;
	private Category category;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomeUrl() {
		return homeUrl;
	}
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	public String getScriptPath() {
		return scriptPath;
	}
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "SiteConfig [name=" + name + ", homeUrl=" + homeUrl + ", scriptPath=" + scriptPath + ", category="
				+ category + "]";
	}

	

}
