package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.enums.Sites;

import javafx.scene.image.Image;

public class PageInfo {
	private String title;
	private String url;
	private int totalItems;
	private long time;
	private Image imageSmall;
	private Image imageFull;
	private Sites site;
	
	public PageInfo(Image imageSmall, Image imageFull) {
		this.imageSmall = imageSmall;
		this.imageFull = imageFull;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Image getImageSmall() {
		return imageSmall;
	}
	public Image getImageFull() {
		return imageFull;
	}
	public Sites getSite() {
		return site;
	}
	public void setSite(Sites site) {
		this.site = site;
	}

}
