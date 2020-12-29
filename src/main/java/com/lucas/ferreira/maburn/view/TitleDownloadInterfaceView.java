package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.TitleDownloadInterfaceController;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class TitleDownloadInterfaceView extends ViewInterface {
	private CollectionItem title;
	private Pane root;
	private MainInterfaceView mainView;
	private TitleInterfaceView titleInterfaceView;
	private FXMLLoader loader = new FXMLLoader();
	private boolean initializeIsDone = false;

	public TitleDownloadInterfaceView(TitleInterfaceView titleInterfaceView) {
		// TODO Auto-generated constructor stub
		this.titleInterfaceView = titleInterfaceView;
		this.title = titleInterfaceView.getTitle();
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
				initFXMLLoader(new TitleDownloadInterfaceController(mainView, this), root, "TitleDownloadViewFXML.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}
	
	public TitleInterfaceView getTitleInterfaceView() {
		return titleInterfaceView;
	}

}
