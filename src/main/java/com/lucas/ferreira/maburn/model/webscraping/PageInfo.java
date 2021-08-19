package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

import javafx.scene.image.Image;

public class PageInfo {
	private String title;
	private String url;
	private int totalItems;
	private long time;
	private Image imageSmall;
	private Image imageFull;
	private RegisteredSite registeredSite;

	public PageInfo(Image imageSmall, Image imageFull) {
		this.imageSmall = imageSmall;
		this.imageFull = imageFull;
	}

	public PageInfo() {

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

	public void setImageSmall(Image imageSmall) {
		this.imageSmall = imageSmall;
	}

	public Image getImageFull() {
		return imageFull;
	}

	public void setImageFull(Image imageFull) {
		this.imageFull = imageFull;
	}

	public RegisteredSite getRegisteredSite() {
		return registeredSite;
	}

	public void setRegisteredSite(RegisteredSite registeredSite) {
		this.registeredSite = registeredSite;
	}

}
