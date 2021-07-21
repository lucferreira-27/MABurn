package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.FileModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileExtractValues {

	private String path;
	private IntegerProperty totalFiles = new SimpleIntegerProperty();
	private IntegerProperty currentFile = new SimpleIntegerProperty();;
	private DoubleProperty extractingProgress = new SimpleDoubleProperty();
	private ObservableList<FileModel> fileModels = FXCollections.observableArrayList();
	private BooleanProperty finish = new SimpleBooleanProperty(false);
	private BooleanProperty failed = new SimpleBooleanProperty(false);

	public FileExtractValues(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IntegerProperty getTotalFiles() {
		return totalFiles;
	}

	public IntegerProperty getCurrentFile() {
		return currentFile;
	}

	public DoubleProperty getExtractingProgress() {
		return extractingProgress;
	}

	public ObservableList<FileModel> getFileModels() {
		return fileModels;
	}

	public BooleanProperty getFailed() {
		return failed;
	}

	public BooleanProperty getFinish() {
		return finish;
	}

	@Override
	public String toString() {
		return "[From: " + path + "]\n" + 
				"[Extracting: " + currentFile.get() + "/" + totalFiles.get() + "]\n" +
				"[Recent File:" +  (totalFiles.get() > 0   ?  " ---- " : fileModels.get(currentFile.get()).getName()) + "]\n"+
				"[Progress: "+ extractingProgress.get()+ "]\n"; 
	}

}
