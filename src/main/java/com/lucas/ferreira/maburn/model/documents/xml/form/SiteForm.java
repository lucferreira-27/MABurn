package com.lucas.ferreira.maburn.model.documents.xml.form;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class SiteForm {
	private String itemLink;
	private Sites siteName;
	
	public String getItemLink() {
		return itemLink;
	}
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}
	public Sites getSiteName() {
		return siteName;
	}
	public void setSiteName(Sites site) {
		this.siteName = site;
	}
	
	
	
	
}
