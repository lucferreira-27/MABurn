package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.util.List;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface GroupDownloadValues extends DownloadValues {
	
	
	 List<ItemDownloadValues> getListItemsDownloadValues();
	 DoubleProperty getDownloadPageSpeed();
	 IntegerProperty getTotalPagesDownloaded();	
	 DoubleProperty getTimeRemain();
	 ObjectProperty<DownloadProgressState> getDownloadProgressState();
}
