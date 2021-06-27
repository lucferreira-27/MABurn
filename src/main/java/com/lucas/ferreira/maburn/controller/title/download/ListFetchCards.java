package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ListFetchCards {
	private VBox vBox;
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();
	private List<FetchCardController> cardsControllers = new ArrayList<FetchCardController>();

	
	public ListFetchCards(VBox vBox) {
		this.vBox = vBox;
	}
	

	public void add(FetchCard fetchCard ,FetchCardValues fetchCardValues) {
		loadCardFxml((Initializable) fetchCard, fetchCardValues.getItemName());
		addCardController(fetchCard, fetchCardValues);
	}

	private void addCardController(FetchCard fetchCard ,FetchCardValues fetchCardValues) {
		FetchCardController fetchCardController = new FetchCardController(fetchCard,
				fetchCardValues);
		cardsControllers.add(fetchCardController);
		


		fetchCardController.initialize();

	}

	public void loadCardFxml(Initializable initializable, String name) {
		StackPane stackPane = new StackPane();
		try {
			StackPane item = fxmlViewLoader.load(CardFXML.FETCH_CARD.getFxml(), initializable, stackPane);
			item.setUserData(name);
			Platform.runLater(() -> vBox.getChildren().add(item));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
