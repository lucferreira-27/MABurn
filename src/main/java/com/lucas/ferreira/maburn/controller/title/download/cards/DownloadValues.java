package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.beans.property.DoubleProperty;

public interface DownloadValues {
	
	public String getName();
	void setName(String name);
	public DoubleProperty getDownloadProgress();
	
}
