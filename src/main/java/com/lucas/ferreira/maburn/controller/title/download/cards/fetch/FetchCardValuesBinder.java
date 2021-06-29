package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import javafx.application.Platform;

public class FetchCardValuesBinder {
	private FetchCard fetchCard;
	private FetchCardValues fetchCardValues;

	public void binder(FetchCard fetchCard, FetchCardValues fetchCardValues) {

		this.fetchCard = fetchCard;
		this.fetchCardValues = fetchCardValues;

		Platform.runLater(() -> setAllCardText());
		Platform.runLater(() -> addAllCardFetchListener());

	}

	private void setAllCardText() {
		setCardFetchItemName();
		setCardFetchItemUrl();
		setCardFetchState();

	}

	private void addAllCardFetchListener() {
		addCardFetchStateListener();
	}

	private void setCardFetchState() {
		fetchCard.getLabelFetchState().setText(fetchCardValues.getFetchCardState().get().getName());
	}

	private void setCardFetchItemName() {
		fetchCard.getLabelItemName().setText(fetchCardValues.getItemName());

	}

	private void setCardFetchItemUrl() {
		fetchCard.getLabelItemUrl().setText(fetchCardValues.getItemUrl());
	}

	private void addCardFetchStateListener() {
		fetchCardValues.getFetchCardState().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> fetchCard.getLabelFetchState().setText(newvalue.getName()));
		});
	}

}
