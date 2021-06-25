package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface DownloadValues {

	public StringProperty getName();
	
	public StringProperty getTarget();

	public DoubleProperty getDownloadProgress();

	public ObjectProperty<DownloadProgressState> getDownloadProgressState();

	public DoubleProperty getDownloadSize();

	public DoubleProperty getDownloadSpeed();

	public DoubleProperty getTotalDownloaded();

	public DoubleProperty getTimeRemain();

}
