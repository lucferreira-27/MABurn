package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.HomeInterfaceController;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeInterfaceView extends ViewInterface {

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
			CustomLogger.log("> Run HomeInterfaceView");
			initializeIsDone = true;

		});
	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("HomeViewFXML.fxml"));
		HomeInterfaceController controller = new HomeInterfaceController();
		loader.setController(controller);
		try {
			root = loader.<VBox>load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	private void initRoot() {
		initFXMLLoader();

	}

}
