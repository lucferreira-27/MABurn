package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.TitleDownloadInterfaceController;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class TitleDownloadInterfaceView extends ViewInterface {
	private Pane root;
	private TitleInterfaceView titleInterfaceView;
	private boolean initializeIsDone = false;

	public TitleDownloadInterfaceView(TitleInterfaceView titleInterfaceView) {
		// TODO Auto-generated constructor stub
		this.titleInterfaceView = titleInterfaceView;
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
				initFXMLLoader(new TitleDownloadInterfaceController(this), root, "TitleDownloadViewFXML.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CustomLogger.log("> Run TitleInterfaceView");
			initializeIsDone = true;

		});
	}
	
	public TitleInterfaceView getTitleInterfaceView() {
		return titleInterfaceView;
	}

}
