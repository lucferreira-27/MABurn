package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.DonwloadQueueInterfaceController;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DownloadQueueInterfaceView extends ViewInterface{

	private Pane root;
	private DonwloadQueueInterfaceController controller = new DonwloadQueueInterfaceController();

	public DownloadQueueInterfaceView() {

	}

	@Override
	public void loadMainInterfaceFX() {
		// TODO Auto-generated method stub

		this.root = MainInterfaceView.getInstance().getRoot();
		new Thread(() -> {

			remove(root); // Removes the previous nodes.
			initFX(); // Initializes interface.

		}).start();

	}

	private void initFX() {
		CustomLogger.log("> Run ItensInterfaceView");
		Platform.runLater(() -> {

			initFXMLLoader(controller, root, "DownloadQueueView.fxml");

		});

	}

	@Override
	protected void initFXMLLoader(Initializable initializable, Pane root, String fxml) {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(controller);
		try {
			
			root = loader.<VBox>load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
