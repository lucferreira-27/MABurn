package com.lucas.ferreira.maburn.model.webscraping;

import javafx.scene.image.Image;

public class PageInfo {
	private String title;
	private String url;
	private Image imageSmall;
	private Image imageFull;
	
	public PageInfo(String title, Image imageSmall, Image imageFull) {
		this.title = title;
		this.imageSmall = imageSmall;
		this.imageFull = imageFull;
	}

	public String getTitle() {
		return title;
	}

	public Image getImageSmall() {
		return imageSmall;
	}
	public Image getImageFull() {
		return imageFull;
	}

}
