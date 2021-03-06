package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardFull;

import javafx.scene.Node;

public class FetchCardFull implements CardFull<FetchCardController, FetchCard, FetchCardValues> {

	private FetchCardController fetchCardController;
	private FetchCard fetchCard;
	private FetchCardValues fetchCardValues;
	private Node node;
	
	@Override
	public FetchCardController getCardController() {
		return fetchCardController;
	}

	@Override
	public void setCardController(FetchCardController fetchCardController) {
		this.fetchCardController = fetchCardController;

	}

	@Override
	public FetchCard getCard() {

		return fetchCard;
	}

	@Override
	public void setCard(FetchCard fetchCard) {
		this.fetchCard = fetchCard;

	}

	@Override
	public FetchCardValues getCardValues() {
		return fetchCardValues;
	}

	@Override
	public void setCardValues(FetchCardValues fetchCardValues) {
		this.fetchCardValues = fetchCardValues;
	}

	@Override
	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}

}
