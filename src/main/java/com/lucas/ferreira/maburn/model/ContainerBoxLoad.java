package com.lucas.ferreira.maburn.model;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.model.enums.Containers;
import com.lucas.ferreira.maburn.view.FXMLViewLoader;

import javafx.scene.Node;

public class ContainerBoxLoad<T extends Node> {

	private FXMLViewLoader<T> fxmlViewLoader = new FXMLViewLoader<T>();

	public T load(Containers container) throws Exception {
		T box = fxmlViewLoader.loadContainer(container.getFxml(), container.getModelInitialize());
		return box;
	}

	public Initialize setContainerController(Containers container, ModelInitialiable model) {

		switch (container) {
		case BROWSE_INSTALLER:
			return new BrowserInstallerController(model);

		default:
			break;
		}
		return null;

	}

}
