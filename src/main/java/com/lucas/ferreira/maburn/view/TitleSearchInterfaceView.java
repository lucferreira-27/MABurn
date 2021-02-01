package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.TittleSearchInterfaceController;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class TitleSearchInterfaceView extends ViewInterface {
	private CollectionItem title;
	private Pane root;
	private ItemsInterfaceView itensView;
	private boolean initializeIsDone = false;
	
	public TitleSearchInterfaceView(ItemsInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.itensView = itensView;
		this.title = itensView.getController().getCollection().getActualItem();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CustomLogger.log("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}


	private void initRoot() throws IOException {
		initFXMLLoader(new TittleSearchInterfaceController(this, itensView),root ,"TitleSearchViewFXML.fxml");

	}
	
	public CollectionItem getTitle() {
		return title;
	}


}
