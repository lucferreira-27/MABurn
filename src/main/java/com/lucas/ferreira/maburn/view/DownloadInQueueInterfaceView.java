package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.DownloadInQueueController;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class DownloadInQueueInterfaceView extends ViewInterface {
	
	private DownloadInQueueController controller = new DownloadInQueueController();
	
	public DownloadInQueueInterfaceView(TitleDownload titleDownload, BorderPane pane) {
		// TODO Auto-generated constructor stub
		this.root = pane;
		root.setUserData(titleDownload);

	}
	
	@Override
	public void loadMainInterfaceFX() {
		// TODO Auto-generated method stub

		new Thread(() -> {

			remove(root); // Removes the previous nodes.
			initFX(); // Initializes interface.

		}).start();

	}

	private void initFX() {
		CustomLogger.log("> Run ItensInterfaceView");
		Platform.runLater(() -> {

			initFXMLLoader(controller, root, "DownloadInQueueInterfaceView.fxml");

		});

	}

	@Override
	protected void initFXMLLoader(Initializable initializable, Pane root, String fxml) {
		FXMLLoader loader = new FXMLLoader();
		
		loader.setRoot(root);
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(controller);

		try {
			
			root = loader.<BorderPane>load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
