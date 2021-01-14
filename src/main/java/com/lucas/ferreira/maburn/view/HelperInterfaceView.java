package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.HelperInterfaceController;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HelperInterfaceView extends ViewInterface {

	private Pane root;

	private boolean initializeIsDone = false;

	@Override
	public void loadMainInterfaceFX() {
		// TODO Auto-generated method stub
		
		this.root = MainInterfaceView.getInstance().getRoot();
		remove(root);
		initFX();
		while (!initializeIsDone) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private void initFX() {

		Platform.runLater(() -> {

			initRoot();
			initCollectionMenuPane();
			CustomLogger.log("> Run HelperInterfaceView");
			initializeIsDone = true;

		});
	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("HelperViewFXML.fxml"));
		HelperInterfaceController controller = new HelperInterfaceController();
		loader.setController(controller);
		try {
			root = loader.<VBox>load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initCollectionMenuPane() {

	}

	private void initRoot() {
		initFXMLLoader();

	}

}
