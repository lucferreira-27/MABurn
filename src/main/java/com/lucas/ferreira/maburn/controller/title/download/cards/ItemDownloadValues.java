package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public interface ItemDownloadValues extends DownloadValues {
	public String getDirectLink();
	public DoubleProperty getDownloadSize();
	public DoubleProperty getDownloadSpeed();
	
}
