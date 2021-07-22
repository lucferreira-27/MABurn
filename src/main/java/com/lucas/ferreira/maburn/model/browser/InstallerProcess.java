package com.lucas.ferreira.maburn.model.browser;

import java.util.function.Supplier;

import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.enums.InstallationState;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class InstallerProcess {
	private FileDownloadValues fileDownloadValues;
	private FileExtractValues fileExtractValues;
	private ObjectProperty<InstallationState> installationState = new SimpleObjectProperty<InstallationState>(InstallationState.CONNECTING);

	public InstallerProcess(FileExtractValues fileExtractValues, FileDownloadValues fileDownloadValues) {
		this.fileDownloadValues = fileDownloadValues;
		this.fileExtractValues = fileExtractValues;
	}
	

	
	public FileDownloadValues getFileDownloadValues() {
		return fileDownloadValues;
	}

	public String getFileLocal() {
		return fileExtractValues.getPath();
	}

	public ObjectProperty<InstallationState> getInstallationState() {
		return installationState;
	}

	public void setFileExtractValues(FileExtractValues extractValues) {
		this.fileExtractValues = extractValues;
	}

	public FileExtractValues getFileExtractValues() {
		return fileExtractValues;
	}
}
