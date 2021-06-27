package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.IconsInitializer;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.scene.control.Label;

public class EpisodeCardIcons extends IconsInitializer {

	private EpisodeCard episodeCard;
	private EpisodeCardController episodeCardController;

	public EpisodeCardIcons(EpisodeCard episodeCard, EpisodeCardController episodeCardController) {
		this.episodeCard = episodeCard;
		this.episodeCardController = episodeCardController;
	}

	@Override
	protected void initialize() {
		initializeIcons();
		initializeLabelIcons();
	}

	private void initializeLabelIcons() {
		Icon iconLink = new Icon(episodeCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		iconLink.setProperties();
		Label labelDownloadLink = episodeCard.getLabelDownloadLink();
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

	private void initializeIcons() {

		Icon downloadIcon = new Icon(episodeCard.getImageViewDownloadIcon(), new IconConfig(ICON_PATH, Icons.DOWNLOAD_IN_CARD));
		Icon linkIcon = new Icon(episodeCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		Icon iconPlay = new Icon(episodeCard.getImageViewPlayerIcon(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		Icon iconPause = new Icon(episodeCard.getImageViewPauseIcon(), new IconConfig(ICON_PATH, Icons.PAUSE_IN_CARD));
		Icon iconStop = new Icon(episodeCard.getImageViewStopIcon(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));

		downloadIcon.setProperties();

		linkIcon.setProperties();

		iconPlay.setProperties(event -> {
			System.out.println("PLAY");
			episodeCardController.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);
		});

		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			episodeCardController.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});

		iconStop.setProperties(event -> {
			System.out.println("STOP");
			episodeCardController.stop();
		});
	}

}
