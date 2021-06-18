package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;

public interface DownloadValues {

	public String getName();

	void setName(String name);

	public DoubleProperty getDownloadProgress();

	public ObjectProperty<DownloadProgressState> getDownloadProgressState();

	public DoubleProperty getDownloadSize();

	public DoubleProperty getDownloadSpeed();

	public DoubleProperty getTotalDownloaded();

	public DoubleProperty getTimeRemain();

}
