package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PageDownloadItemValues  implements ItemDownloadValues{
	
	private String directLink;
	private String name;
	private DoubleProperty downloadProgress = new SimpleDoubleProperty();
	private DoubleProperty downloadSize  = new SimpleDoubleProperty();
	private DoubleProperty downloadSpeed  = new SimpleDoubleProperty();
	
	
	
	public PageDownloadItemValues(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}


	@Override
	public String getDirectLink() {
		// TODO Auto-generated method stub
		return directLink;
	}

	@Override
	public DoubleProperty getDownloadProgress() {
		// TODO Auto-generated method stub
		return downloadProgress;
	}

	@Override
	public DoubleProperty getDownloadSize() {
		// TODO Auto-generated method stub
		return downloadSize;
	}

	@Override
	public DoubleProperty getDownloadSpeed() {
		// TODO Auto-generated method stub
		return downloadSpeed;
	}

	@Override
	public DoubleProperty getTotalDownloaded() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty<DownloadProgressState> getDownloadProgressState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty getTimeRemain() {
		// TODO Auto-generated method stub
		return null;
	}


}
