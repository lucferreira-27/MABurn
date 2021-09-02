package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerModel;
import com.lucas.ferreira.maburn.model.ContainerBoxLoad;
import com.lucas.ferreira.maburn.model.enums.Containers;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class BrowserInstallerLaunch {
	private final ContainerBoxLoad<BorderPane> containerBoxLoad = new ContainerBoxLoad<BorderPane>();
	private final Containers containers = Containers.BROWSE_INSTALLER;
	private final ShadeLayer shadeLayer = new ShadeLayer(new AnchorPane());

	public BrowserInstallerController openBrowserInstaller(StackPane stackPane) throws IOException {

		BrowserInstallerModel browserInstallerModel = (BrowserInstallerModel) containers.getModelInitialize();

		BorderPane borderPane = loadContainer();
		BrowserInstallerController browserInstallerController = loadController(browserInstallerModel);
		
		browserInstallerController.addOnClose(() -> removeContainerFromStackPane(stackPane, borderPane));
		addContainerInStackPane(stackPane, borderPane);

		return browserInstallerController;

	}
	private BorderPane loadContainer() throws IOException {
		return containerBoxLoad.load(Containers.BROWSE_INSTALLER);
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
