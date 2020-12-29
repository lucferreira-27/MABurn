package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class ViewInterface {

	protected Pane root;

	protected abstract void loadMainInterfaceFX(MainInterfaceView mainView);

	protected void remove(Pane root) {
		Platform.runLater(() -> {
			if (root.getChildren().size() > 1)
				root.getChildren().remove(1, root.getChildren().size());

		});
	}
	

	
	protected void initFXMLLoader(Initializable initializable,Pane root ,String fxml) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
	
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(initializable);

		root = loader.<VBox>load();

	}

}
