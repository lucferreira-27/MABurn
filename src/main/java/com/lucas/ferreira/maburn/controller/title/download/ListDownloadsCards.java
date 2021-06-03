package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ListDownloadsCards {
	private VBox vBox;
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();

	
	public ListDownloadsCards(VBox vBox) {
		// TODO Auto-generated constructor stub
		this.vBox = vBox;
	}
	
	public void add(ItemDownloadValues downloadValues) {
		StackPane stackPane = new StackPane();
		DownloadCardController initializable = new DownloadCardController(downloadValues);
		try {
			StackPane item = fxmlViewLoader.load(Components.DOWNLOAD_CARD.getFxml(), initializable, stackPane);
			System.out.println(item);
			Platform.runLater(() -> vBox.getChildren().add(item));
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
