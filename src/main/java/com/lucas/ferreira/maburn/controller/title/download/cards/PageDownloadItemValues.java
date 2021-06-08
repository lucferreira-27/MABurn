package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PageDownloadItemValues  implements ItemDownloadValues{
	
	private String directLink;
	private String name;
	private DoubleProperty downloadProgress = new SimpleDoubleProperty();
	private DoubleProperty downloadSize  = new SimpleDoubleProperty();
	private DoubleProperty downloadSpeed  = new SimpleDoubleProperty();
	private DoubleProperty timeRemain = new SimpleDoubleProperty();
	private DoubleProperty totalDownloaded  = new SimpleDoubleProperty();
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>(DownloadProgressState.WAITING);

	
	
	public PageDownloadItemValues() {
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

		return directLink;
	}
	@Override
	public void setDirectLink(String directLink) {
		
		this.directLink = directLink;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[File: " + name + " - Size: " + DataStorageUtil.converterUnit(downloadSize.get()) +  " - Download State: " + downloadProgressState.get() +"]";
	}



}
