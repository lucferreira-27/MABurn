package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardValues;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ListFetchCards {
	private VBox vBox;
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();

	
	public ListFetchCards(VBox vBox) {
		// TODO Auto-generated constructor stub
		this.vBox = vBox;
	}
	
	public void add(FetchCardValues fetchCardValues) {
		StackPane stackPane = new StackPane();
		FetchCardController initializable = new FetchCardController(fetchCardValues);
		try {
			StackPane item = fxmlViewLoader.load(Components.FETCH_CARD.getFxml(), initializable, stackPane);
			System.out.println(item);
			Platform.runLater(() -> vBox.getChildren().add(item));
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
