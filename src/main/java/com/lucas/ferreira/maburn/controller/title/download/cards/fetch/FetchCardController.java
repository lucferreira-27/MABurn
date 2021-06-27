package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class FetchCardController {

	private FetchCardValues fetchCardValues;
	private FetchCard fetchCard;
	private String ICON_PATH = "icons/";
	private AnimationOpacityCard animationOpacityCard;

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
		initializeLabelIcons();
		initializeAnimation();
	}

	public void initializeAnimation() {
		animationOpacityCard = new AnimationOpacityCard(fetchCard.getRoot());
		fetchCard.getRoot().setOpacity(0);
		Platform.runLater(() -> {
			fetchCard.getRoot().setOpacity(0);
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});
	}

	public void initializeLabelIcons() {
		Icon iconLink = new Icon(fetchCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		iconLink.setProperties(event -> {
			System.out.println(fetchCardValues.getItemUrl());
		});
		Label labelDownloadLink = fetchCard.getLabelItemUrl();
		LabelIcon labelIcon = new LabelIcon(iconLink, labelDownloadLink);
		labelIcon.setOnMousePressedLabel(label -> {
			label.setUnderline(true);
			ClipboardSystem.setContent(label.getText());
		}

		);

		labelIcon.setOnMouseReleasedLabel(label -> label.setUnderline(false));
		labelIcon.setOnMouseEnteredIcon(icon -> icon.swithIconColor());
		labelIcon.setOnMouseExitedIcon(icon -> icon.swithIconColor());
	}

	public void initializeIcons() {
		Icon iconFetch = new Icon(fetchCard.getImageViewFetchIcon(), new IconConfig(ICON_PATH, Icons.FETCH_IN_CARD));
		iconFetch.setProperties();

	}

}
