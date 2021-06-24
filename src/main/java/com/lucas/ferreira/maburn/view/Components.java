package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.collection.menu.CollectionMenuController;
import com.lucas.ferreira.maburn.controller.menu.MenuController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardController;

import javafx.fxml.Initializable;

public enum Components {
	MENU("MenuViewFXML.fxml", new MenuController()),
	COLLECTION_MENU("CollectionImagesMenu.fxml", new CollectionMenuController()),
	ITEM_PANEL("ItemPanel.fxml", new ItemPanel());

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
