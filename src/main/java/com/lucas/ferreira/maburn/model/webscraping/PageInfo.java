package com.lucas.ferreira.maburn.model.webscraping;

import javafx.scene.image.Image;

public class PageInfo {
	private String title;
	private String url;
	private Image image;
	
	public PageInfo(String title, Image image) {
		this.title = title;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public Image getImage() {
		return image;
	}

}
