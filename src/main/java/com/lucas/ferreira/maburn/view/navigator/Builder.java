package com.lucas.ferreira.maburn.view.navigator;

import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.FXMLViewLoader;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class Builder {

	public void build(Pane root, Components components) {

	}

	public Pane build(Pane root, Components components, Object userData) {
		root.setUserData(userData);

		String fxml = components.getFxml();
		Initializable initializable = components.getController();
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.addCompomentInRoot(fxml, initializable, root);
		return root;
	}
	public Pane build(Pane root,String fxml ,Initializable initializable, Object userData) {
		root.setUserData(userData);

		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.addCompomentInRoot(fxml, initializable, root);
		return root;
	}
}
