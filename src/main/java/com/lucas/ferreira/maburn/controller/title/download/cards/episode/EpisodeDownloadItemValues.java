package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EpisodeDownloadItemValues implements ItemDownloadValues {

	private DoubleProperty downloadProgress = new SimpleDoubleProperty();
	private DoubleProperty downloadSize = new SimpleDoubleProperty();
	private DoubleProperty totalDownloaded = new SimpleDoubleProperty();
	private DoubleProperty downloadSpeed = new SimpleDoubleProperty();
	private DoubleProperty timeRemain = new SimpleDoubleProperty();
	private StringProperty resolution = new SimpleStringProperty();
	private StringProperty target = new SimpleStringProperty();
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>();

	private StringProperty name = new SimpleStringProperty();;
	private StringProperty directLink = new SimpleStringProperty();;

	public EpisodeDownloadItemValues() {

	}

	public StringProperty getDirectLink() {
		return directLink;
	}
	
	public StringProperty getName() {
		return name;
	}
	@Override
	public StringProperty getTarget() {
		return target;
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
		return String.format("[Name: %s - Size: %s - Download State: %s - Speed: %s]", name,
				DataStorageUtil.converterUnit(downloadSize.get()), downloadProgressState.get(),
				DataStorageUtil.converterSpeedUnit(downloadSpeed.get()))
				+ "\n"
				+ String.format("[Total Downloaded: %s - Time Remain: %s - Progress: %s]",
						DataStorageUtil.converterUnit(totalDownloaded.get()), timeRemain.get(),
						(downloadProgress.get() * 100))
				+ "\n" + String.format("[Download link: %s - Resolution: %s]", directLink, resolution.get());
	}




}
