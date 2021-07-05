package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardInteractIcons;
import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.IconsInitializer;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EpisodeCardIcons extends IconsInitializer {

	
	private DownloadCardInteractIcons downloadCardInteractIcons;
	private EpisodeCard episodeCard;
	private EpisodeCardController episodeCardController;
	
	public EpisodeCardIcons(EpisodeCard episodeCard, EpisodeCardController episodeCardController) {
		this.episodeCard = episodeCard;
		this.episodeCardController = episodeCardController;
	}

	@Override
	protected void initialize() throws FileNotFoundException {
		initializeIcons();
		initializeLabelIcons();
	}

	private void initializeLabelIcons() {
		Icon iconLink = new Icon(episodeCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
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


	private void initializeIcons() throws FileNotFoundException{

	
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewDownloadIcon(), Icons.DOWNLOAD_IN_CARD);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewPlayerIcon(), Icons.PLAY_IN_CARD);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewPauseIcon(), Icons.PAUSE_IN_CARD);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewStopIcon(), Icons.STOP_IN_CARD);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewWatchIcon(), Icons.WATCH_ICON);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewOpenFolderIcon(), Icons.OPEN_FOLDER_ICON);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewDeleteIcon(), Icons.DELETE_ICON);
		createNewIconAndSetImageViewIcon(episodeCard.getImageViewRefreshIcon(), Icons.REFRESH_ICON);

		
		downloadCardInteractIcons = new DownloadCardInteractIcons(episodeCardController, episodeCard);
		downloadCardInteractIcons.interactTurnOn();
		


	}

}
