package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardController;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.effects.CardFadeInOutAnimation;
import com.lucas.ferreira.maburn.model.effects.FadeInOutAnimation;

import javafx.application.Platform;

public class FetchCardController implements CardController {

	private FetchCardValues fetchCardValues;
	private FetchCardIcons fetchCardIcons;
	private FetchCard fetchCard;
	private FadeInOutAnimation fadeInOutAnimation;

	public FetchCardController(FetchCard fetchCard, FetchCardValues fetchCardValues) {
		this.fetchCard = fetchCard;
		this.fetchCardValues = fetchCardValues;
	}

	public FetchCardController initialize() {
		initializeValuesCard();
		initializeIcons();
		initializeAnimation();
		return this;
	}

	private void initializeValuesCard() {
		FetchCardValuesBinder fetchCardValuesBinder = new FetchCardValuesBinder();
		fetchCardValuesBinder.binder(fetchCard, fetchCardValues);
	}

	private void initializeIcons() {
		fetchCardIcons = new FetchCardIcons(fetchCard);
		fetchCardIcons.initialize();
	}

	private void initializeAnimation() {
		AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(fetchCard.getRoot());
		fadeInOutAnimation = new CardFadeInOutAnimation(animationOpacityCard);
	}

	public void show() {
		fadeInOutAnimation.fadeIn();
	}

	public void hide() {
		fadeInOutAnimation.fadeOut();
	}

	public void abort() {
		
	}
}
