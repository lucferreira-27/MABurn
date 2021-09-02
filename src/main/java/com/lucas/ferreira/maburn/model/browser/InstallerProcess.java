package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.enums.InstallationState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class InstallerProcess {
	private final FileDownloadValues fileDownloadValues;
	private final FileExtractValues fileExtractValues;
	private final ObjectProperty<InstallationState> installationState = new SimpleObjectProperty<>(InstallationState.CONNECTING);

	public InstallerProcess(FileExtractValues fileExtractValues, FileDownloadValues fileDownloadValues) {
		this.fileDownloadValues = fileDownloadValues;
		this.fileExtractValues = fileExtractValues;
	}

	public FileDownloadValues getFileDownloadValues() {
		return fileDownloadValues;
	}

	public ObjectProperty<InstallationState> getInstallationState() {
		return installationState;
	}

	public FileExtractValues getFileExtractValues() {
		return fileExtractValues;
	}
}
