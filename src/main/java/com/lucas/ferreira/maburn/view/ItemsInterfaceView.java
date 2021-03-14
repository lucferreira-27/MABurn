package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.ItemsInterfaceController;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ItemsInterfaceView extends ViewInterface {

	private Pane root;
	private ItemsInterfaceController controller;
	private DataFetcher dataFetcher;
	public ItemsInterfaceView(DataFetcher dataFetcher) {
		this.dataFetcher = dataFetcher;

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

			initFXMLLoader(controller, root, "ItensViewFXML.fxml");

		});

	}

	@Override
	protected void initFXMLLoader(Initializable initializable, Pane root, String fxml) {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("ItensViewFXML.fxml"));
		if (controller == null)
			controller = new ItemsInterfaceController(this);
		loader.setController(controller);
		try {
			
			root = loader.<VBox>load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
	public DataFetcher getDataFetcher() {
		return dataFetcher;
	}
	
	public ItemsInterfaceController getController() {
		return controller;
	}

}
