package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardInteractIcons;
import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.IconsInitializer;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
	private Icon createNewIconAndSetImageViewIcon(ImageView imageView, Icons icon) {

		Icon newIcon = newIcon(imageView, icon);
		setImageViewIcon(newIcon, imageView);
		return newIcon;
	}


	private Icon newIcon(ImageView imageView, Icons icon) {
		Icon newIcon = new Icon(imageView, new IconConfig(ICON_PATH, icon));
		return newIcon;
	}

	private void setImageViewIcon(Icon newIcon, ImageView imageView) {
		imageView.setUserData(newIcon);
	}
	private void initializeLabelIcons() {
		Icon iconLink = new Icon(chapterCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
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
		LabelIcon labelPages = new LabelIcon(pagesIcon, labelTotalPages);

		labelPages.setOnMouseEnteredIcon(icon -> icon.swithIconColor());
		labelPages.setOnMouseExitedIcon(icon -> icon.swithIconColor());
	}

	private void initializeIcons() {
		
		
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewDownloadIcon(), Icons.DOWNLOAD_IN_CARD);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewPlayerIcon(), Icons.PLAY_IN_CARD);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewPauseIcon(), Icons.PAUSE_IN_CARD);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewStopIcon(), Icons.STOP_IN_CARD);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewOpenFolderIcon(), Icons.OPEN_FOLDER_ICON);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewReadIcon(), Icons.READ_ICON);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewDeleteIcon(), Icons.DELETE_ICON);
		createNewIconAndSetImageViewIcon(chapterCard.getImageViewRefreshIcon(), Icons.REFRESH_ICON);
		DownloadCardInteractIcons chapterCardInteractIcons = new DownloadCardInteractIcons(chapterCardController, chapterCard);
		chapterCardInteractIcons.interactTurnOn();



	}

}
