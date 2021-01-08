package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.TitleInterfaceController;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class TitleInterfaceView extends ViewInterface {
	private Collections collections;
	private CollectionItem title;
	private Pane root;
	private MainInterfaceView mainView;
	private ItemsInterfaceView itensView;
	private FXMLLoader loader = new FXMLLoader();
	private boolean initializeIsDone = false;
	
	public TitleInterfaceView(ItemsInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.collections = itensView.getCollections();
		this.itensView = itensView;
		this.title = itensView.getCollections().getActualItem();
	}
	
	@Override
	public void loadMainInterfaceFX(MainInterfaceView mainView) {
		// TODO Auto-generated method stub
		this.mainView = mainView;
		this.root = mainView.getRoot();

		new Thread(() -> {
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
		}).start();

	}



	private void initFX() {

		Platform.runLater(() -> {

			try {
				initRoot();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CustomLogger.log("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}


	private void initRoot() throws IOException {
		initFXMLLoader(new TitleInterfaceController(mainView, this, itensView),root ,"TitleViewFXML.fxml");

	}
	
	public CollectionItem getTitle() {
		return title;
	}



}
