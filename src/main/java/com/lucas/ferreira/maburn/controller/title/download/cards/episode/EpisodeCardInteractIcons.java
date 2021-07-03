package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.util.Icon;

public class EpisodeCardInteractIcons {
	private EpisodeCard episodeCard;
	private EpisodeCardController episodeCardController;
	public EpisodeCardInteractIcons(EpisodeCardController episodeCardController, EpisodeCard episodeCard) {
		this.episodeCard = episodeCard;
		this.episodeCardController = episodeCardController;
	}
	

	
	public void interactTurnOn() {

		onActiveIconOpenFolder();
		onActiveIconPause();
		onActiveIconPlay();
		onActiveIconStop();
		onActiveIconWatch();
	}

	
	private void onActiveIconPlay() {
		Icon iconPlay = (Icon) episodeCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) episodeCard.getImageViewPauseIcon().getUserData();
		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			episodeCardController.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});
	}

	private void onActiveIconStop() {
		Icon iconStop = (Icon) episodeCard.getImageViewStopIcon().getUserData();
		iconStop.setProperties(event -> {
			System.out.println("STOP");
			episodeCardController.stop();
		});
	}

	private void onActiveIconPause() {
		Icon iconPlay = (Icon) episodeCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) episodeCard.getImageViewPauseIcon().getUserData();

		iconPause.setProperties(event -> {
			System.out.println("PLAY");
			episodeCardController.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);
		});
	}

	private void onActiveIconOpenFolder() {
		Icon iconOpenFolder = (Icon) episodeCard.getImageViewOpenFolderIcon().getUserData();

		iconOpenFolder.setProperties(event -> {
			episodeCardController.openFolder();

		});
	}

	private void onActiveIconWatch() {
		Icon iconWatch = (Icon) episodeCard.getImageViewWatchIcon().getUserData();

		iconWatch.setProperties(event -> {
			episodeCardController.openTitleMedia();

		});
	}

}
