package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.download.FileDownloadValues;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InstallerProcess {
	private FileDownloadValues fileDownloadValues;
	private FileExtractValues fileExtractValues;
	private String fileLocal;
	private BooleanProperty processFailed = new SimpleBooleanProperty(false);
	private BooleanProperty processSuccess = new SimpleBooleanProperty(false);
	
	public InstallerProcess(FileExtractValues fileExtractValues, FileDownloadValues fileDownloadValues) {
		this.fileDownloadValues  = fileDownloadValues;
		this.fileExtractValues = fileExtractValues;
	}
	
	public FileDownloadValues getFileDownloadValues() {
		return fileDownloadValues;
	}
	public String getFileLocal() {
		return fileExtractValues.getPath();
	}

	public BooleanProperty getProcessFailed() {
		return processFailed;
	}
	public BooleanProperty getProcessSuccess() {
		return processSuccess;
	}
	public void setFileExtractValues(FileExtractValues extractValues) {
		this.fileExtractValues = extractValues;
	}
	public FileExtractValues getFileExtractValues() {
		return fileExtractValues;
	}
}
