package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public abstract class Downloader<T> extends Task<T> {

	protected CollectionSubItem subItem;
	
	protected List<File> listFile;
	
	protected List<String> listLink;
	
	protected Sites site;
	
	protected static final int BUFFER_SIZE = 8192;
	
	protected DoubleProperty downloadProgress = new SimpleDoubleProperty();

	protected DoubleProperty sizeProperty = new SimpleDoubleProperty();

	protected DoubleProperty speedProperty = new SimpleDoubleProperty();

	protected BooleanProperty pauseProperty = new SimpleBooleanProperty();

	protected abstract File download() throws IOException;

	protected void updateSize(double lenght) {
		sizeProperty.set(lenght);
	}
	
	protected void updateSpeed(double speed) {
		speedProperty.set(speed);
	}
	
	public abstract void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, Sites sites);
	
	public abstract DoubleProperty getDownloadProgress();
	
	public abstract DoubleProperty getSizeProperty();
	
	public abstract DoubleProperty getDownloadSpeedProperty();

	public abstract BooleanProperty getPauseProperty();
	
	public abstract double speedCalculation();

	public abstract void pause();

	public abstract void unpause();
	
	@Override
	protected T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
