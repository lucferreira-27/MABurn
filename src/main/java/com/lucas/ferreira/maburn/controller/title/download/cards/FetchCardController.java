package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.application.Platform;

public class FetchCardController  {



	private FetchCardValues fetchCardValues;
	private FetchCard fetchCard;
	private String ICON_PATH = "icons/";
	private AnimationOpacityCard animationOpacityCard;

	public FetchCardController(FetchCard fetchCard ,FetchCardValues fetchCardValues) {
		this.fetchCard = fetchCard;
		this.fetchCardValues = fetchCardValues;
	}

	public void initializeValuesCard() {
		FetchCardValuesBinder fetchCardValuesBinder = new FetchCardValuesBinder();
		fetchCardValuesBinder.binder(fetchCard, fetchCardValues);
	}

	public void initialize() {
		initializeValuesCard();
		initializeIcons();
		initializeAnimation();
	}
	
	
	public void initializeAnimation() {
		animationOpacityCard = new AnimationOpacityCard(fetchCard.getRoot());
		fetchCard.getRoot().setOpacity(0);
		Platform.runLater(() ->{
			fetchCard.getRoot().setOpacity(0);
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});
	}
	
	public void initializeIcons() {
		Icon iconFetch = new Icon(fetchCard.getImageViewFetchIcon(), new IconConfig(ICON_PATH, Icons.FETCH_IN_CARD));
		iconFetch.setProperties();

		Icon iconLink = new Icon(fetchCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		iconLink.setProperties(event -> {
			System.out.println(fetchCardValues.getItemUrl());
		});
	}

}
