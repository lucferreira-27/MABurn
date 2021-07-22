package com.lucas.ferreira.maburn.model.enums;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerModel;
import com.lucas.ferreira.maburn.model.ModelInitialiable;

public enum Containers {
	MANUAL_FETCH("",null),
	BROWSE_INSTALLER("BrowserInstallBox.fxml",new BrowserInstallerModel());
	
	
	private String fxml;
	private ModelInitialiable modelInitialiable;
	
	private Containers(String fxml, ModelInitialiable modelInitialiable) {
		this.fxml = fxml;
		this.modelInitialiable =modelInitialiable;
	}
	
	public String getFxml() {
		return fxml;
	}
	public ModelInitialiable getModelInitialize() {
		return modelInitialiable;
	}
}
