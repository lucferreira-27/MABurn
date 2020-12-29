package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.HomeInterfaceController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeInterfaceView extends ViewInterface {

	private Pane root;
	private MainInterfaceView mainView;

	private boolean initializeIsDone = false;

	@Override
	public void loadMainInterfaceFX(MainInterfaceView mainView) {
		// TODO Auto-generated method stub
		this.mainView = mainView;
		this.root = mainView.getRoot();
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
			System.out.println("> Run HomeInterfaceView");
			initializeIsDone = true;

		});
	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("HomeViewFXML.fxml"));
		HomeInterfaceController controller = new HomeInterfaceController(mainView);
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
