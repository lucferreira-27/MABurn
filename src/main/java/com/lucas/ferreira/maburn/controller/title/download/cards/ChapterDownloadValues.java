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

public class ChapterDownloadValues implements GroupDownloadValues {

	private String name;
	private DoubleProperty downloadProgress = new SimpleDoubleProperty();;
	private IntegerProperty totalPagesDownloaded = new SimpleIntegerProperty();
	private DoubleProperty totalDownlaodConclued = new SimpleDoubleProperty();
	private DoubleProperty downloadPageSpeed = new SimpleDoubleProperty();
	private DoubleProperty timeRemain = new SimpleDoubleProperty();
	private DoubleProperty chapterSize = new SimpleDoubleProperty();
	private List<ItemDownloadValues> listPageDownloadItemValues = new ArrayList<ItemDownloadValues>();
	private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>();
	private ObservableList<ItemDownloadValues> obsListPageDownlaodItemsValues = FXCollections.observableArrayList();



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

	public ObservableList<ItemDownloadValues> getObsListNewPageDownloadItemsValues() {
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

	public DoubleProperty getChapterSize() {
		return chapterSize;
	}

	@Override
	public String toString() {
		return String.format(
				"Name: %s\nDownloadProgress: %s\nTotalPagesDownloaded: %s\nDownloadPageSpeed: %s\nSize: %s\nTimeRemain: %s\nListPageDownloadItemValues:\n%s\nDownloadProgressState: %s\n",
				name, downloadProgress.get(), totalPagesDownloaded.get(), downloadPageSpeed.get(), chapterSize.get(), timeRemain.get(),
				listPageDownloadItemValues.stream().map(DownloadValues::toString)
						.collect(Collectors.joining("\n")),
				downloadProgressState.get());
	}

	@Override
	public DoubleProperty getDownloadSize() {
		// TODO Auto-generated method stub
		return chapterSize;
	}

	@Override
	public DoubleProperty getDownloadSpeed() {
		// TODO Auto-generated method stub
		return downloadPageSpeed;
	}

	@Override
	public DoubleProperty getTotalDownloaded() {
		// TODO Auto-generated method stub
		return totalDownlaodConclued;
	}

}
