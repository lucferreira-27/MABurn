package com.lucas.ferreira.maburn.model.download.queue;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public abstract class Downloader<T> extends Task<T> {

	private boolean firstInstance = true;

	protected HttpURLConnection httpConn;

	protected CollectionSubItem subItem;

	protected List<File> listFile;

	protected List<String> listLink;

	protected ItemWebData webData;

	protected static final int BUFFER_SIZE = 8192;

	protected SimpleStringProperty nameProperty = new SimpleStringProperty();

	protected SimpleDoubleProperty sizeProperty = new SimpleDoubleProperty();

	protected SimpleDoubleProperty speedProperty = new SimpleDoubleProperty();

	protected SimpleDoubleProperty completedProperty = new SimpleDoubleProperty();

	protected SimpleIntegerProperty downloadFile = new SimpleIntegerProperty();

	protected SimpleStringProperty stateProperty = new SimpleStringProperty();

	protected SimpleDoubleProperty downloadProgress = new SimpleDoubleProperty();

	protected SimpleStringProperty actionPauseProperty = new SimpleStringProperty();

	protected SimpleStringProperty actionCancelProperty = new SimpleStringProperty();

	protected BooleanProperty pauseProperty = new SimpleBooleanProperty();

	protected BooleanProperty cancelProperty = new SimpleBooleanProperty();

	public Downloader() {
		// TODO Auto-generated constructor stub
		stateProperty.set(String.valueOf(DownloadState.PREPARING));
		cancelProperty.set(false);
		pauseProperty.set(false);
	}

	public String getName() {

		return nameProperty.get();
	}

	public String getDownloadState() {
		return stateProperty.get();
	}

	public Double getSize() {
		return sizeProperty.get();
	}

	public Double getSpeed() {
		return speedProperty.get();
	}

	public Double getCompleted() {
		return completedProperty.get();
	}

	public Double getDownloadProgress() {
		return downloadProgress.get();
	}

	public String getActionPause() {
		return actionPauseProperty.get();
	}

	public String getActionCancel() {
		return actionCancelProperty.get();
	}

	public boolean isFirstInstance() {
		return firstInstance;
	}

	public CollectionSubItem getSubItem() {
		return subItem;
	}

	public ItemWebData getItemWebData() {
		return webData;
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	public BooleanProperty getCancelProperty() {
		return cancelProperty;
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	public SimpleDoubleProperty sizeProperty() {
		return sizeProperty;
	}

	public SimpleDoubleProperty speedProperty() {
		return speedProperty;
	}

	public SimpleDoubleProperty completedProperty() {
		return completedProperty;
	}

	public SimpleIntegerProperty downloadFileProperty() {
		return downloadFile;
	}

	public SimpleStringProperty downloadStateProperty() {
		return stateProperty;
	}

	public SimpleStringProperty actionPauseProperty() {
		return actionPauseProperty;
	}

	public SimpleStringProperty actionCancelProperty() {
		return actionCancelProperty;
	}

	public SimpleDoubleProperty getDownloadProgressProperty() {
		return downloadProgress;
	}

	public HttpURLConnection getHttpConn() {
		return httpConn;
	}

	public void setDownloadState(DownloadState state) {
		stateProperty.set(String.valueOf(state));
	}

	protected abstract File download() throws IOException;

	protected void updateSize(double completed) {
		sizeProperty.set(completed);
	}

	protected void updateCompleted(double lenght) {

		completedProperty.set(lenght);

	}

	protected void updateDonwloadFile(int n) {
		downloadFile.set(n);
	}

	protected void updateSpeed(double speed) {

		speedProperty.set(speed);
	}

	protected void updateName(String name) {
		nameProperty.set(name);
	}

	protected void updateState(DownloadState state) {
		stateProperty.set(String.valueOf(state));
	}

	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, ItemWebData webData) {
		this.listLink = listLink;
		this.subItem = subItem;
		this.listFile = listFile;
		this.webData = webData;
		cancelProperty.set(false);
		pauseProperty.set(false);
		updateMessage("[" + webData.getName() + "]");

	}

	public abstract long speedCalculation(Double downloadSpeed, long start, long end, int i);

	public abstract void pause();

	public abstract void resume();

	public void kill() {
		pauseProperty.set(false);
		cancelProperty.set(true);
		firstInstance = false;
	}

	public void reset() {
		pauseProperty.set(false);
		cancelProperty.set(false);
	}

	@Override
	protected T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Downloader [\nnameProperty=" + nameProperty + ", \nsizeProperty=" + sizeProperty + ", \nspeedProperty="
				+ speedProperty + ", \ncompletedProperty=" + completedProperty + ", \nstateProperty=" + stateProperty
				+ ", \nprogressProperty=" + null + ", \npauseProperty=" + pauseProperty + ", \ncancelProperty="
				+ cancelProperty + ", \nfetched= " + webData.isFetched() + "]";
	}

}
