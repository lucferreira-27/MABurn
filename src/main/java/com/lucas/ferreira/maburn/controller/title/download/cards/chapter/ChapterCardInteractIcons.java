package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.util.Icon;

public class ChapterCardInteractIcons {
	private ChapterCard chapterCard;
	private ChapterCardController chapterCardController;
	public ChapterCardInteractIcons(ChapterCardController chapterCardController, ChapterCard chapterCard) {
		this.chapterCard = chapterCard;
		this.chapterCardController = chapterCardController;
	}
	

	
	public void interactTurnOn() {

		onActiveIconPause();
		onActiveIconPlay();
		onActiveIconStop();
		onActiveIconRead();
		onActiveIconOpenFolder();

	}

	
	private void onActiveIconPlay() {
		Icon iconPlay = (Icon) chapterCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) chapterCard.getImageViewPauseIcon().getUserData();
		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			chapterCardController.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});
	}

	private void onActiveIconStop() {
		Icon iconStop = (Icon) chapterCard.getImageViewStopIcon().getUserData();
		iconStop.setProperties(event -> {
			System.out.println("STOP");
			chapterCardController.stop();
		});
	}

	private void onActiveIconPause() {
		Icon iconPlay = (Icon) chapterCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) chapterCard.getImageViewPauseIcon().getUserData();

		iconPause.setProperties(event -> {
			System.out.println("PLAY");
			chapterCardController.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);
		});
	}

	private void onActiveIconOpenFolder() {
		Icon iconOpenFolder = (Icon) chapterCard.getImageViewOpenFolderIcon().getUserData();

		iconOpenFolder.setProperties(event -> {
			System.out.println("Open Folder");
			chapterCardController.openFolder();

		});
	}

	private void onActiveIconRead() {
		Icon iconRead = (Icon) chapterCard.getImageViewReadIcon().getUserData();

		iconRead.setProperties(event -> {
			System.out.println("Read ");
			chapterCardController.openTitleMedia();

		});
	}

}
