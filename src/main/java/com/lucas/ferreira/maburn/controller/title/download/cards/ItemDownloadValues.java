package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ItemDownloadValues extends DownloadValues {
	public StringProperty getDirectLink();
	public DoubleProperty getDownloadSize();
	public DoubleProperty getDownloadSpeed();
	public DoubleProperty getTotalDownloaded();	
	public DoubleProperty getTimeRemain();
	public ObjectProperty<DownloadProgressState> getDownloadProgressState();
}
