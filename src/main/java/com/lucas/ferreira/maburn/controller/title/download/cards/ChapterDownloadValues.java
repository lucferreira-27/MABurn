package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChapterDownloadValues  implements GroupDownloadValues{
	
	
	private String name;
	private DoubleProperty downloadProgress  = new SimpleDoubleProperty();;
	private IntegerProperty totalPagesDownloaded  = new SimpleIntegerProperty();
	private DoubleProperty downloadPageSpeed  = new SimpleDoubleProperty();
	private DoubleProperty timeRemain = new SimpleDoubleProperty();
	private List<ItemDownloadValues> listPageDownloadItemValues = new ArrayList<ItemDownloadValues>();
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>();
	private ObservableList<ItemDownloadValues> obsListPageDownlaodItemsValues = FXCollections.observableArrayList();
	
	public ChapterDownloadValues(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public DoubleProperty getDownloadProgress() {
		return downloadProgress;
	}

	@Override
	public List<ItemDownloadValues> getListItemsDownloadValues() {
		// TODO Auto-generated method stub
		return listPageDownloadItemValues;
	}
	public ObservableList<ItemDownloadValues> getObsListPageDownlaodItemsValues() {
		return obsListPageDownlaodItemsValues;
	}


	@Override
	public DoubleProperty getDownloadPageSpeed() {

		return downloadPageSpeed;
	}

	@Override
	public IntegerProperty getTotalPagesDownloaded() {

		return totalPagesDownloaded;
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
		return String.format(
				"ChapterDownloadValues [name=%s, downloadProgress=%s, totalPagesDownloaded=%s, downloadPageSpeed=%s, timeRemain=%s, listPageDownloadItemValues=%s, downloadProgressState=%s]",
				name, downloadProgress, totalPagesDownloaded, downloadPageSpeed, timeRemain, listPageDownloadItemValues.stream().map(DownloadValues::getName).collect(Collectors.joining(", ", "\n", "")),
				downloadProgressState);
	}




		
	

}
