package com.lucas.ferreira.maburn.model.browser;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerModel;
import com.lucas.ferreira.maburn.model.ContainerBoxLoad;
import com.lucas.ferreira.maburn.model.enums.Containers;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import com.sun.rowset.internal.InsertRow;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class BrowserInstallerLaunch {
	private ContainerBoxLoad<BorderPane> containerBoxLoad = new ContainerBoxLoad<BorderPane>();
	private Containers containers = Containers.BROWSE_INSTALLER;
	private ShadeLayer shadeLayer = new ShadeLayer(new AnchorPane());

	public BrowserInstallerController openBrowserInstaller(StackPane stackPane) throws IOException {

		BrowserInstallerModel browserInstallerModel = (BrowserInstallerModel) containers.getModelInitialize();

		BorderPane borderPane = loadContainer();
		BrowserInstallerController browserInstallerController = loadController(browserInstallerModel);
		
		browserInstallerController.addOnClose(() -> {
			removeContainerFromStackPane(stackPane, borderPane);
			return null;
		});
		addContainerInStackPane(stackPane, borderPane);

		return browserInstallerController;

	}
	private BorderPane loadContainer() throws IOException {
		BorderPane borderPane = containerBoxLoad.load(Containers.BROWSE_INSTALLER);
		return borderPane;
	}
	
	private BrowserInstallerController loadController(BrowserInstallerModel browserInstallerModel) {
		return (BrowserInstallerController) containerBoxLoad.setContainerController(Containers.BROWSE_INSTALLER,
				browserInstallerModel);
	}
	private void removeContainerFromStackPane(StackPane stackPane, BorderPane borderPane) {
		stackPane.getChildren().remove(borderPane);
		stackPane.getChildren().remove(shadeLayer.getRecShade());
	}

	private void addContainerInStackPane(StackPane stackPane, BorderPane borderPane) {
		StackPane.setAlignment(borderPane, Pos.TOP_CENTER);
		StackPane.setMargin(borderPane, new Insets(30, 0, 0, 0));
		shadeLayer.show();
		Platform.runLater(() ->{
			stackPane.getChildren().add(shadeLayer.getRecShade());
			stackPane.getChildren().add(borderPane);
		});

	}




}
