package com.lucas.ferreira.maburn.model.download;

import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;

import javafx.beans.property.ObjectProperty;

public interface DownloadAdapter {
	
	public ObjectProperty<ItemDownloadValues> download(DownloadFilename downloadFilename,List<String> links);
}
