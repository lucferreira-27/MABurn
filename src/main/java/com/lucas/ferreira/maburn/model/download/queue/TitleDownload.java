package com.lucas.ferreira.maburn.model.download.queue;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.AlertWindowView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

public abstract class TitleDownload extends Task<CollectionSubItem> {

	protected String name;
	protected Collections collections;
	protected List<ItemWebData> items = new ArrayList<ItemWebData>();
	protected List<Downloader<CollectionSubItem>> downloads = new ArrayList<Downloader<CollectionSubItem>>();
	protected IntegerProperty id = new SimpleIntegerProperty();

	protected DoubleProperty sizeProperty = new SimpleDoubleProperty();
	protected DoubleProperty speedProperty = new SimpleDoubleProperty();
	protected DoubleProperty completedProperty = new SimpleDoubleProperty();
	protected BooleanProperty pauseProperty = new SimpleBooleanProperty();
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

	public TitleDownload() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CollectionSubItem call() throws Exception {
		// TODO Auto-generated method stub
		
		items.forEach(item -> {
			addItem(item);

		});
		return null;
	}

	public void addItem(ItemWebData item) {
		if (!item.isFetched())
			throw new DownloadServiceException("Item need be fetched first!");
		item.download(collections);
		Downloader<CollectionSubItem> downloader = item.getDownloader();
		setName(item.getName());
		checkProgress(downloader);
		downloads.add(downloader);

	}

	public abstract void checkProgress(Downloader<CollectionSubItem> downloader);

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

}
