package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.effects.CardFadeInOutAnimation;
import com.lucas.ferreira.maburn.model.effects.FadeInOutAnimation;

import javafx.application.Platform;

public class FetchCardController {

	private FetchCardValues fetchCardValues;
	private FetchCardIcons fetchCardIcons;
	private FetchCard fetchCard;
	private FadeInOutAnimation fadeInOutAnimation;
	public FetchCardController(FetchCard fetchCard, FetchCardValues fetchCardValues) {
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

	public void initializeIcons() {
		fetchCardIcons = new FetchCardIcons(fetchCard);
		fetchCardIcons.initialize();
	}

	public void initializeAnimation() {
		AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(fetchCard.getRoot());
		fadeInOutAnimation = new CardFadeInOutAnimation(animationOpacityCard);
		fadeInOutAnimation.fadeIn();

	}

}
