package com.lucas.ferreira.maburn.controller.title.download.cards.icons;

import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.view.InteractIcons;

public class DownloadCardInteractIcons  {
	private DownloadCard downloadCard;
	private DownloadCardController downloadCardController;
	private PlayerIcons playerIcons;
	private CompletedDownloadIcons completedDownloadIcons;
	private FailedDownloadIcons failedDownloadIcons;

	public DownloadCardInteractIcons(DownloadCardController downloadCardController, DownloadCard downloadCard) {
		this.downloadCard = downloadCard;
		this.downloadCardController = downloadCardController;
	}

	public void interactTurnOn() {

		playerIcons = new PlayerIcons(downloadCard, downloadCardController);
		completedDownloadIcons = new CompletedDownloadIcons(downloadCard, downloadCardController);
		failedDownloadIcons = new FailedDownloadIcons(downloadCard, downloadCardController);

		List<InteractIcons> cardIntereactIcons = Arrays.asList(playerIcons, completedDownloadIcons,
				failedDownloadIcons);

		addAllCardInteraectIcons(cardIntereactIcons);

	}

	private void addAllCardInteraectIcons(List<InteractIcons> cardIntereactIcons) {
		cardIntereactIcons.forEach(card -> {
			card.interactTurnOn();
		});
	}

	public DownloadCard getDownloadCard() {
		return downloadCard;
	}

	public CompletedDownloadIcons getCompletedDownloadIcons() {
		return completedDownloadIcons;
	}

	public PlayerIcons getPlayerIcons() {
		return playerIcons;
	}

	public FailedDownloadIcons getFailedDownloadIcons() {
		return failedDownloadIcons;
	}



}
