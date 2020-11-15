package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.HomeInterfaceController;
import com.lucas.ferreira.maburn.controller.TitleInterfaceController;
import com.lucas.ferreira.maburn.model.collections.Collections;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TitleInterfaceView implements ViewInterface {
	private Collections collections;
	private Pane root;
	private MainInterfaceView mainView;
	private ItensInterfaceView itensView;
	private FXMLLoader loader = new FXMLLoader();
	private boolean initializeIsDone = false;
	
	public TitleInterfaceView(ItensInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.collections = itensView.getCollections();
		this.itensView = itensView;
	}
	
	@Override
	public void loadMainInterfaceFX(MainInterfaceView mainView) {
		// TODO Auto-generated method stub
		this.mainView = mainView;
		this.root = mainView.getRoot();

		new Thread(() -> {
			remove();
			initFX();
			while (!initializeIsDone) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void remove() {
		Platform.runLater(() -> {
			if (root.getChildren().size() > 1)
				root.getChildren().remove(1, root.getChildren().size());

		});
	}

	private void initFX() {

		Platform.runLater(() -> {

			initRoot();
			System.out.println("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("TitleViewFXML.fxml"));
		TitleInterfaceController controller = new TitleInterfaceController(mainView, this, itensView);
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
