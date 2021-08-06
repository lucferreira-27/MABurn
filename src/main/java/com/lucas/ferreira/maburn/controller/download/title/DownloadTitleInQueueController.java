package com.lucas.ferreira.maburn.controller.download.title;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.lucas.ferreira.maburn.controller.Controller;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadListCard;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.LabelIcon;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class DownloadTitleInQueueController implements Controller {
	private static final String ICON_PATH = "icons/";
	private TitleQueuePlayerIcons queuePlayerIcons;
	private DownloadTitleInQueueModal modal;
	private TitleDownloadListCard titleDownloadListCard;
	private Title title;
	private BooleanProperty select = new SimpleBooleanProperty(false);
	private BooleanProperty mouseHover = new SimpleBooleanProperty(false);

	public DownloadTitleInQueueController(DownloadTitleInQueueModal modal,
			TitleDownloadListCard titleDownloadListCard) {
		this.modal = modal;
		this.titleDownloadListCard = titleDownloadListCard;
		this.title = titleDownloadListCard.getTitle();
	}

	@Override
	public void initialize() {
		initializeIcons();
		initializeTitleInfos();
		intializeTitleDownloadInfos();
		initializeControllers();
	}

	private void initializeControllers() {

		onMouseClick();
		onMouseHover();

	}

	private void onMouseClick() {

		modal.getRoot().setOnMouseClicked(click -> {
			select();
			openItems();
		});

	}

	public void select() {
		
		showContainer();
		select.set(true);

	}

	private void openItems() {
		titleDownloadListCard.getDownloadList().getContentDownloadList().getDownloadCardFulls().forEach(full -> {
			if (title.getCollectionTitle().getCategory() == Category.MANGA) {
				ChapterDownloadValues chapterDownloadValues = (ChapterDownloadValues) full.getCardValues();
				System.out.println(chapterDownloadValues);
			}
			if (title.getCollectionTitle().getCategory() == Category.ANIME) {
				EpisodeDownloadItemValues episodeDownloadItemValues = (EpisodeDownloadItemValues) full.getCardValues();
				System.out.println(episodeDownloadItemValues);

			}
		});
	}

	private void onMouseHover() {
		modal.getRoot().hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue) {
				mouseHover.set(true);
			} else {
				mouseHover.set(false);
			}
		});
		mouseHover.addListener((obs, oldvalue, newvalue) -> {
			if (select.get()) {
				return;
			}

			if (newvalue) {
				showContainer();
			} else {
				hideContainer();

			}
		});
	}
	private void showContainer() {
		modal.getHbShade().setVisible(false);
		modal.getRoot().setStyle("-fx-border-color: white;");
	}
	private void hideContainer() {
		modal.getHbShade().setVisible(true);
		modal.getRoot().setStyle("-fx-border-color: gray;");
	}



	private void intializeTitleDownloadInfos() {

		Platform.runLater(() -> {
			int value = titleDownloadListCard.getDownloadList().getNavDownloadList().getCompletedCards().get();
			modal.getLblCompleted().setText(String.valueOf(value));
		});
		Platform.runLater(() -> {
			int value = titleDownloadListCard.getDownloadList().getNavDownloadList().getDownloadingCards().get();
			modal.getLblDownloads().setText(String.valueOf(value));
		});
		Platform.runLater(() -> {
			int value = titleDownloadListCard.getDownloadList().getNavDownloadList().getFailedCards().get();
			modal.getLblErros().setText(String.valueOf(value));
		});

		titleDownloadListCard.getDownloadList().getNavDownloadList().getCompletedCards()
				.addListener((obs, oldvalue, newvalue) -> {

					Platform.runLater(() -> {
						modal.getLblCompleted().setText(String.valueOf(newvalue.intValue()));
					});
				});

		titleDownloadListCard.getDownloadList().getNavDownloadList().getDownloadingCards()
				.addListener((obs, oldvalue, newvalue) -> {
					Platform.runLater(() -> {
						modal.getLblDownloads().setText(String.valueOf(newvalue.intValue()));
					});

				});

		titleDownloadListCard.getDownloadList().getNavDownloadList().getFailedCards()
				.addListener((obs, oldvalue, newvalue) -> {
					Platform.runLater(() -> {
						modal.getLblErros().setText(String.valueOf(newvalue.intValue()));
					});
				});
	}

	private void initializeTitleInfos() {

		String strTittle = title.getCollectionTitle().getTitleDataBase();
		modal.getLblTitle().setText(strTittle);

		Image image = title.getCollectionTitle().getImage();
		modal.getImgTitleCover().setImage(image);

		String dest = title.getCollectionTitle().getDestination();
		modal.getLblFolder().setText(dest);
	}

	private void initializeIcons() {
		Icon iconComplete = new Icon(modal.getImgCompleted(), new IconConfig(ICON_PATH, Icons.COMPLETED_ICON));
		Icon iconError = new Icon(modal.getImgErros(), new IconConfig(ICON_PATH, Icons.ERROR_ICON));
		Icon iconDownloading = new Icon(modal.getImgDownloads(), new IconConfig(ICON_PATH, Icons.DOWNLOADING_ICON));
		Icon iconLaunch = new Icon(modal.getImgLaunch(), new IconConfig(ICON_PATH, Icons.LAUNCH_ICON));
		iconLaunch.setProperties(click -> openTitleDownload());
		Icon iconPause = new Icon(modal.getImgPause(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		Icon iconPlay = new Icon(modal.getImgPlay(), new IconConfig(ICON_PATH, Icons.PAUSE_IN_CARD));
		Icon iconStop = new Icon(modal.getImgStop(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));
		Icon iconTime = new Icon(modal.getImgTime(), new IconConfig(ICON_PATH, Icons.TIME_ICON));
		Icon iconFolder = new Icon(modal.getImgFolder(), new IconConfig(ICON_PATH, Icons.OPEN_FOLDER_ICON));

		LabelIcon labelIconFolder = new LabelIcon(iconFolder, modal.getLblFolder());
		labelIconFolder.setOnMouseHover((label) -> {

			labelIconFolder.alterIconColor();
			LabelIcon.underline(label, true);

		}, (label) -> {
			labelIconFolder.alterIconColor();
			LabelIcon.underline(label, false);
		});
		labelIconFolder.setOnMousePressedDbLabel((label) -> {
			try {
				
				labelIconFolder.alterIconColor();
				LabelIcon.underline(label, false);
				Desktop.getDesktop().open(new File(label.getText()));
				LabelIcon.underline(label, true);
				labelIconFolder.alterIconColor();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		queuePlayerIcons = new TitleQueuePlayerIcons(modal, this);
		queuePlayerIcons.interactTurnOn();

	}

	private void openTitleDownload() {
		Navigator navigator = new Navigator();
		try {
			navigator.openFromRegisteredState(Interfaces.TITLE_DOWNLOAD,
					String.valueOf(title.getCollectionTitle().getId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		titleDownloadListCard.getDownloadList().stop();
		queuePlayerIcons.disableAll();

	}

	public void resume() {
		titleDownloadListCard.getDownloadList().resume();

	}

	public void pause() {
		titleDownloadListCard.getDownloadList().pause();

	}

	public void unselect() {
		hideContainer();
		select.set(false);
	}

	public BooleanProperty getSelect() {
		return select;
	}

}
