package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.CollectionInterfaceController;
import com.lucas.ferreira.maburn.controller.DonwloadQueueInterfaceController;
import com.lucas.ferreira.maburn.controller.DownloadCardController;
import com.lucas.ferreira.maburn.controller.HelperInterfaceController;
import com.lucas.ferreira.maburn.controller.HomeInterfaceController;
import com.lucas.ferreira.maburn.controller.TitleDownloadController;
import com.lucas.ferreira.maburn.controller.TitleDownloadInterfaceController;
import com.lucas.ferreira.maburn.controller.TitleInterfaceController;
import com.lucas.ferreira.maburn.controller.TittleSearchInterfaceController;

import javafx.fxml.Initializable;

public enum Interfaces {

	HOME("HomeViewFXML.fxml", new HomeInterfaceController()),
	TITLE("TitleViewFXML.fxml", new TitleInterfaceController()),
	TITLE_SEARCH("TitleSearchViewFXML.fxml", new TittleSearchInterfaceController()),
	TITLE_DOWNLOAD("TitleDownloadViewFXML.fxml", new TitleDownloadInterfaceController()),
	TITLE__DOWNLOAD("TitleDownload.fxml", new TitleDownloadController()),

	DOWNLOADS("DownloadQueueView.fxml", new DonwloadQueueInterfaceController()),
	CONFIGURATION("HelperViewFXML.fxml", new HelperInterfaceController()), 
	CALENDAR("", null),
	COLLECTION("ItemsViewFXML.fxml", new CollectionInterfaceController()),
	DOWNLOAD_CARD("DownloadCard.fxml", new DownloadCardController());

	private Initializable controller;
	private String fxml;

	private Interfaces(String fxml, Initializable controller) {
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
