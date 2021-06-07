package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EpisodeDownloadItemValues implements ItemDownloadValues{
	
	private DoubleProperty downloadProgress = new SimpleDoubleProperty();
	private DoubleProperty downloadSize  = new SimpleDoubleProperty();
	private DoubleProperty totalDownloaded  = new SimpleDoubleProperty();
	private DoubleProperty downloadSpeed  = new SimpleDoubleProperty();
	private DoubleProperty timeRemain = new SimpleDoubleProperty();
	private StringProperty resolution = new SimpleStringProperty();
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>();
	
	private String name;
	private String directLink;

	public EpisodeDownloadItemValues(String itemName, String directLink) {
		this.name = itemName;
		this.directLink = directLink;
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

	@Override
	public DoubleProperty getTotalDownloaded() {
		// TODO Auto-generated method stub
		return totalDownloaded;
	}

	@Override
	public ObjectProperty<DownloadProgressState> getDownloadProgressState() {
		// TODO Auto-generated method stub
		return downloadProgressState;
	}

	@Override
	public DoubleProperty getTimeRemain() {
		// TODO Auto-generated method stub
		return timeRemain;
	}
	public StringProperty getResolution() {
		return resolution;
	}

	@Override
	public String toString() {
		return String.format(
				"EpisodeDownloadItemValues [\nDownloadProgress = [%s], \nDownloadSize = [%s], \nTotalDownloaded = [%s], \nDownloadSpeed = [%s], \nTimeRemain=[%s], \nResolution = [%s], \nDownloadProgressState = [%s], \nName = [%s], \nDirectLink = [%s]]",
				downloadProgress.get(), downloadSize.get(), totalDownloaded.get(), downloadSpeed.get(), timeRemain.get(), resolution.get(),
				downloadProgressState.get(), name, directLink);
	}
	




	

}
