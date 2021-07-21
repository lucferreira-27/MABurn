package com.lucas.ferreira.maburn.model.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileDownloadValues implements ItemDownloadValues {
	
	private StringProperty name = new SimpleStringProperty();;
	private StringProperty target = new SimpleStringProperty();;
	private DoubleProperty downloadProgress = new  SimpleDoubleProperty();;
	private StringProperty directLink = new SimpleStringProperty();;
	private DoubleProperty downloadSize = new SimpleDoubleProperty();;
	private DoubleProperty downloadSpeed = new SimpleDoubleProperty();;
	private DoubleProperty totalDownloaded = new SimpleDoubleProperty();;
	private DoubleProperty timeRemain = new SimpleDoubleProperty();;
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>();
	
	@Override
	public StringProperty getName() {
		return name;
	}
	@Override
	public StringProperty getTarget() {
		return target;
	}
	
	@Override
	public DoubleProperty getDownloadProgress() {
		return downloadProgress;
	}
	
	@Override
	public StringProperty getDirectLink() {
		return directLink;
	}
	
	@Override
	public DoubleProperty getDownloadSize() {
		return downloadSize;
	}
	
	@Override
	public DoubleProperty getDownloadSpeed() {
		return downloadSpeed;
	}
	
	@Override
	public DoubleProperty getTotalDownloaded() {
		return totalDownloaded;
	}
	
	@Override
	public DoubleProperty getTimeRemain() {
		return timeRemain;
	}
	
	@Override
	public ObjectProperty<DownloadProgressState> getDownloadProgressState() {
		return downloadProgressState;
	}
	@Override
	public String toString() {
		return String.format("[Name: %s - Size: %s - Download State: %s - Speed: %s]", name,
				DataStorageUtil.converterUnit(downloadSize.get()), downloadProgressState.get(),
				DataStorageUtil.converterSpeedUnit(downloadSpeed.get()))
				+ "\n"
				+ String.format("[Total Downloaded: %s - Time Remain: %s - Progress: %s]",
						DataStorageUtil.converterUnit(totalDownloaded.get()), timeRemain.get(),
						(downloadProgress.get() * 100))
				+ "\n" + String.format("[Download link: %s]", directLink.get());
	}
	


	

}
