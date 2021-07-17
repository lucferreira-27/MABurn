package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.collection.CollectionInterfaceController;
import com.lucas.ferreira.maburn.controller.home.HomeInterfaceController;
import com.lucas.ferreira.maburn.controller.settings.SettingsInterfaceController;
import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownload;
import com.lucas.ferreira.maburn.controller.title.search.TittleSearchInterfaceController;

import javafx.fxml.Initializable;

public enum Interfaces {

	HOME("HomeViewFXML.fxml", new HomeInterfaceController()),
	TITLE("TitleViewFXML.fxml", new TitleController()),
	TITLE_SEARCH("TitleSearchViewFXML.fxml", new TittleSearchInterfaceController()),
//	TITLE_DOWNLOAD("TitleDownloadViewFXML.fxml", new TitleDownloadInterfaceController()),
	TITLE_DOWNLOAD("TitleDownload.fxml", new TitleDownload()),

	DOWNLOADS("DownloadQueueView.fxml", null),
	CONFIGURATION("HelperViewFXML.fxml", new SettingsInterfaceController()), 
	CALENDAR("", null),
	COLLECTION("ItemsViewFXML.fxml", new CollectionInterfaceController()),
	DOWNLOAD_CARD("DownloadCard.fxml", null);

	private Initializable controller;
	private String fxml;

	private Interfaces(String fxml, Initializable controller) {
		
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
