package com.lucas.ferreira.maburn.controller.title.download;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.PaneCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;

import javafx.scene.layout.VBox;

public class ContentFetchList implements ContentList{

	
	private List<FetchCard> fetchCards = new ArrayList<FetchCard>();
	private List<FetchCardController> fetchCardControllers = new ArrayList<FetchCardController>();
	public ContentFetchList() {
	}
	
	public void addCard(CardController cardController, PaneCard paneCard) {
		fetchCardControllers.add((FetchCardController) cardController);
		fetchCards.add((FetchCard) paneCard);
	}
	public List<FetchCardController> getFetchCardControllers() {
		return fetchCardControllers;
	}
	public List<FetchCard> getFetchCards() {
		return fetchCards;
	}

}
