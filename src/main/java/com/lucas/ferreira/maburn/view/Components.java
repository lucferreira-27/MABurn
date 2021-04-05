package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.DownloadInQueueController;
import com.lucas.ferreira.maburn.controller.MenuController;

import javafx.fxml.Initializable;

public enum Components {
	MENU("MenuViewFXML.fxml", new MenuController()),
	DOWNLOAD_IN_QUEUE("DownloadInQueueInterfaceView.fxml", new DownloadInQueueController());

	private Initializable controller;
	private String fxml;

	private Components(String fxml, Initializable controller) {
		// TODO Auto-generated constructor stub
		this.controller = controller;
		this.fxml = fxml;

	}

	public Initializable getController() {
		return controller;
	}

	public String getFxml() {
		return fxml;
	}
}