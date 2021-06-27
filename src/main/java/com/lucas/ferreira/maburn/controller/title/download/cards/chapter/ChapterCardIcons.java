package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.IconsInitializer;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.scene.control.Label;

public class ChapterCardIcons extends IconsInitializer {

	private ChapterCard chapterCard;
	private ChapterCardController chapterCardController;

	public ChapterCardIcons(ChapterCard chapterCard, ChapterCardController chapterCardController) {
		this.chapterCard = chapterCard;
		this.chapterCardController = chapterCardController;
	}

	@Override
	protected void initialize() {
		initializeIcons();
		initializeLabelIcons();
	}

	private void initializeLabelIcons() {
		Icon iconLink = new Icon(chapterCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		iconLink.setProperties();
		Label labelDownloadLink = chapterCard.getLabelDownloadLink();
		LabelIcon labelIcon = new LabelIcon(iconLink, labelDownloadLink);
		labelIcon.setOnMousePressedLabel(label -> {
			label.setUnderline(true);
			ClipboardSystem.setContent(label.getText());
		}

		);
		labelIcon.setOnMouseReleasedLabel(label -> label.setUnderline(false));
		labelIcon.setOnMouseEnteredIcon(icon -> icon.swithIconColor());
		labelIcon.setOnMouseExitedIcon(icon -> icon.swithIconColor());

		Label labelTotalPages = chapterCard.getLabelTotalPagesDownloaded();
		Icon pagesIcon = new Icon(chapterCard.getImageViewPages(), new IconConfig(ICON_PATH, Icons.OPEN_PAGES));
		pagesIcon.setProperties();
		LabelIcon labelPages = new LabelIcon(pagesIcon, labelTotalPages);

		labelPages.setOnMouseEnteredIcon(icon -> icon.swithIconColor());
		labelPages.setOnMouseExitedIcon(icon -> icon.swithIconColor());
	}

	private void initializeIcons() {

		Icon downloadIcon = new Icon(chapterCard.getImageViewDownloadIcon(),
				new IconConfig(ICON_PATH, Icons.DOWNLOAD_IN_CARD));
		Icon iconPlay = new Icon(chapterCard.getImageViewPlayerIcon(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		Icon iconPause = new Icon(chapterCard.getImageViewPauseIcon(), new IconConfig(ICON_PATH, Icons.PAUSE_IN_CARD));
		Icon iconStop = new Icon(chapterCard.getImageViewStopIcon(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));

		downloadIcon.setProperties();

		iconPlay.setProperties(event -> {
			System.out.println("PLAY");
			chapterCardController.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);
		});

		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			chapterCardController.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});

		iconStop.setProperties(event -> {
			System.out.println("STOP");
			chapterCardController.stop();
		});
	}

}
