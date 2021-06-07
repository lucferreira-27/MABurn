package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

public interface DownloadValues {
	
	public String getName();
	void setName(String name);
	public DoubleProperty getDownloadProgress();
	public ObjectProperty<DownloadProgressState> getDownloadProgressState();

}
