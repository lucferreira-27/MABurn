package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.IconsInitializer;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.scene.control.Label;

public class FetchCardIcons extends IconsInitializer {

	private FetchCard fetchCard;

	public FetchCardIcons(FetchCard fetchCard) {
		this.fetchCard = fetchCard;
	}

	@Override
	protected void initialize() {
		initializeIcons();
		initializeLabelIcons();
	}

	private void initializeLabelIcons() {
		Icon iconLink = new Icon(fetchCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		Label labelDownloadLink = fetchCard.getLabelItemUrl();
		LabelIcon labelIcon = new LabelIcon(iconLink, labelDownloadLink);

		labelIcon.setOnMousePressedLabel(label -> {
			label.setUnderline(true);
			ClipboardSystem.setContent(label.getText());
		});

		labelIcon.setOnMouseReleasedLabel(label -> label.setUnderline(false));
		labelIcon.setOnMouseEnteredIcon(icon -> icon.swithIconColor());
		labelIcon.setOnMouseExitedIcon(icon -> icon.swithIconColor());
	}

	private void initializeIcons() {
		Icon iconFetch = new Icon(fetchCard.getImageViewFetchIcon(), new IconConfig(ICON_PATH, Icons.FETCH_IN_CARD));
		Icon linkIcon = new Icon(fetchCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));



	}
}
