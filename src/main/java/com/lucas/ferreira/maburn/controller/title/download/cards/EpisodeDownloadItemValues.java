package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EpisodeDownloadItemValues implements ItemDownloadValues{
	
	private DoubleProperty downloadProgress = new SimpleDoubleProperty();
	private DoubleProperty downloadSize  = new SimpleDoubleProperty();
	private DoubleProperty downloadSpeed  = new SimpleDoubleProperty();
	private String name;
	private String itemUrl;
	private String directLink;

	public EpisodeDownloadItemValues(String itemName, String itemUrl) {
		this.name = itemName;
		this.itemUrl = itemUrl;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getDirectLink() {
		return directLink;
	}
	public void setDirectLink(String directLink) {
		this.directLink = directLink;
	}

	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public DoubleProperty getDownloadProgress() {
		return downloadProgress;
	}
	public void setDownloadProgress(DoubleProperty downloadProgress) {
		this.downloadProgress = downloadProgress;
	}
	public DoubleProperty getDownloadSize() {
		return downloadSize;
	}
	public void setDownloadSize(DoubleProperty downloadSize) {
		this.downloadSize = downloadSize;
	}
	public DoubleProperty getDownloadSpeed() {
		return downloadSpeed;
	}
	public void setDownloadSpeed(DoubleProperty downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}




	

}
