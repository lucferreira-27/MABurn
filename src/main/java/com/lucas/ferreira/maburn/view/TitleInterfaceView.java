package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.ItemsInterfaceController;
import com.lucas.ferreira.maburn.controller.TitleInterfaceController;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class TitleInterfaceView extends ViewInterface {
	private CollectionItem title;
	private Pane root;
	private ItemsInterfaceController itemsController;
	private ItemsInterfaceView itemsView;

	private boolean initializeIsDone = false;
	
	public TitleInterfaceView(ItemsInterfaceController itemsController) {
		// TODO Auto-generated constructor stub
		this.itemsView = itemsController.getItensView();
		this.title = itemsController.getCollection().getActualItem();
	}
	
	public TitleInterfaceView(ItemsInterfaceView itemsView) {
		// TODO Auto-generated constructor stub
		this.itemsView = itemsView;
		this.title = itemsController.getCollection().getActualItem();
	}
	
	@Override
	public void loadMainInterfaceFX() {
		// TODO Auto-generated method stub
		this.root = MainInterfaceView.getInstance().getRoot();

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
				// TODO Auto-genserated catch block
				e.printStackTrace();
			}
			CustomLogger.log("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}


	private void initRoot() throws IOException {
		initFXMLLoader(new TitleInterfaceController(this, itemsView),root ,"TitleViewFXML.fxml");

	}
	
	public CollectionItem getTitle() {
		return title;
	}



}
