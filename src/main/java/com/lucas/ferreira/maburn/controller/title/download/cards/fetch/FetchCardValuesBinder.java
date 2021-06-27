package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import javafx.application.Platform;

public class FetchCardValuesBinder {

	public void binder(FetchCard fetchCard, FetchCardValues fetchCardValues) {
		Platform.runLater(() -> {
			
			fetchCard.getLabelFetchState().setText(fetchCardValues.getFetchCardState().get().getName());
			fetchCardValues.getFetchCardState().addListener((obs, oldvalue, newvalue) -> {
				Platform.runLater(() ->  fetchCard.getLabelFetchState().setText(newvalue.getName()));
			});
			fetchCard.getLabelItemName().setText(fetchCardValues.getItemName());
			fetchCard.getLabelItemUrl().setText(fetchCardValues.getItemUrl());
		});

 	}
}
 