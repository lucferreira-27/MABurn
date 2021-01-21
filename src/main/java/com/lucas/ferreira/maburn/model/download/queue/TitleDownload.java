package com.lucas.ferreira.maburn.model.download.queue;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TitleDownload {

	protected String name;
	protected Collections collections;
	protected List<ItemWebData> items = new ArrayList<ItemWebData>();
	protected List<Downloader<CollectionSubItem>> downloads = new ArrayList<Downloader<CollectionSubItem>>();
	protected IntegerProperty id = new SimpleIntegerProperty();
	private ObservableList<Downloader<CollectionSubItem>> obsDownloads;
	protected DoubleProperty sizeProperty = new SimpleDoubleProperty();
	protected DoubleProperty speedProperty = new SimpleDoubleProperty();
	protected DoubleProperty completedProperty = new SimpleDoubleProperty();
	protected BooleanProperty pauseProperty = new SimpleBooleanProperty();
	protected BooleanProperty cancelProperty = new SimpleBooleanProperty();

	protected DownloadState state;

	public TitleDownload(List<ItemWebData> items, Collections collections) {
		// TODO Auto-generated constructor stub
		if (items.stream().anyMatch(item -> !item.isFetched())) {
			throw new DownloadServiceException("All items need be fetched first!");
		}
		this.items = items;
		id.setValue(collections.getActualItem().getId());
		this.collections = collections;
	}

	public TitleDownload(Collections collections) {
		// TODO Auto-generated constructor stub
		this.collections = collections;
		obsDownloads = FXCollections.observableArrayList(downloads);
	}

	public void addItem(ItemWebData item) {
		if (!item.isFetched())
			throw new DownloadServiceException("Item need be fetched first!");
		item.download(collections);
		Downloader<CollectionSubItem> downloader = item.getDownloader();
		checkProgress(downloader);
		obsDownloads.add(downloader);

	}

	public void checkProgress(Downloader<CollectionSubItem> downloader) {
		// TODO Auto-generated method stub
		downloader.progressProperty().addListener((obs, oldvalue, newvalue) -> {

		});

		downloader.pauseProperty.bindBidirectional(pauseProperty);
		downloader.cancelProperty.bindBidirectional(cancelProperty);

		downloader.sizeProperty().addListener((observable, oldvalue, newvalue) -> {
			sizeProperty.set(sizeProperty.subtract(oldvalue.doubleValue()).get());
			sizeProperty.set(sizeProperty.add(newvalue.doubleValue()).get());
		});

		downloader.downloadStateProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue.equals(String.valueOf(DownloadState.FAILED))
					|| newvalue.equals(String.valueOf(DownloadState.CANCELING))) {

				Double value = downloader.completedProperty().get();
				completedProperty.set(completedProperty.subtract(value).get());

			}

		});

		downloader.setOnFailed(event -> {
			System.err.println("The task failed with the following exception:");
//				AlertWindowView.errorAlert("Download Service", "The task failed with the following exception:",
//						exceptionProperty().get().getMessage());
//				exceptionProperty().get().printStackTrace();

		});

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id.getValue();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public List<ItemWebData> getItems() {
		return items;
	}

	public List<Downloader<CollectionSubItem>> getDownloads() {
		return downloads;
	}

	public ObservableList<Downloader<CollectionSubItem>> getObsDownloads() {
		return obsDownloads;
	}

	public void setItems(List<ItemWebData> items) {
		this.items = items;
	}

	public DoubleProperty getSizeProperty() {
		return sizeProperty;
	}

	public void setSizeProperty(DoubleProperty sizeProperty) {
		this.sizeProperty = sizeProperty;
	}

	public DoubleProperty getSpeedProperty() {
		return speedProperty;
	}

	public void setSpeedProperty(DoubleProperty speedProperty) {
		this.speedProperty = speedProperty;
	}

	public DoubleProperty getCompletedProperty() {
		return completedProperty;
	}

	public void setCompletedProperty(DoubleProperty completedProperty) {
		this.completedProperty = completedProperty;
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	public void setPauseProperty(BooleanProperty pauseProperty) {
		this.pauseProperty = pauseProperty;
	}

	public DownloadState getDownloadState() {
		return state;
	}

	public void setState(DownloadState state) {
		this.state = state;
	}

	public void checkProgress() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		pauseProperty.set(true);
		state = DownloadState.PAUSE;
	}

	public void resume() {
		pauseProperty.set(false);
		state = DownloadState.DOWNLOADING;
	}

	public void cancel() {
		cancelProperty.set(true);
		state = DownloadState.CANCELING;

	}

}
