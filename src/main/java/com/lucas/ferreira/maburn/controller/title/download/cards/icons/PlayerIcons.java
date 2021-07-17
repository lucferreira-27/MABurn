package com.lucas.ferreira.maburn.controller.title.download.cards.icons;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.view.CardInteractIcons;
import com.lucas.ferreira.maburn.view.IconVisibility;

public class PlayerIcons implements CardInteractIcons{
	
	
	private IconVisibility iconVisibility = new IconVisibility();
	private DownloadCard downloadCard;
	private DownloadCardController controller;
	public PlayerIcons(DownloadCard downloadCard, DownloadCardController controller) {
		this.downloadCard = downloadCard;
		this.controller = controller;
	}
	@Override
	public void interactTurnOn() {
		onActiveIconPlay();
		onActiveIconStop();
		onActiveIconPause();
		
	}


	private void onActiveIconPlay() {
		Icon iconPlay = (Icon) downloadCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) downloadCard.getImageViewPauseIcon().getUserData();
		iconPlay.setProperties(event -> {
			
			controller.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);

		});
	}

	private void onActiveIconStop() {
		Icon iconStop = (Icon) downloadCard.getImageViewStopIcon().getUserData();
		iconStop.setProperties(event -> {
			controller.stop();
		});
	}

	private void onActiveIconPause() {
		Icon iconPlay = (Icon) downloadCard.getImageViewPlayerIcon().getUserData();
		Icon iconPause = (Icon) downloadCard.getImageViewPauseIcon().getUserData();

		iconPause.setProperties(event -> {
			
			controller.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});
	}
	
	
	public void disableAll() {
		iconVisibility.disableIcon(downloadCard.getImageViewPauseIcon());
		iconVisibility.disableIcon(downloadCard.getImageViewPlayerIcon());
		iconVisibility.disableIcon(downloadCard.getImageViewStopIcon());
	}
	public void enableAll() {
		iconVisibility.enableIcon(downloadCard.getImageViewPauseIcon());
		iconVisibility.enableIcon(downloadCard.getImageViewPlayerIcon());
		iconVisibility.enableIcon(downloadCard.getImageViewStopIcon());
	}
	public void hideAll() {
		iconVisibility.hideIcon(downloadCard.getImageViewPauseIcon());
		iconVisibility.hideIcon(downloadCard.getImageViewPlayerIcon());
		iconVisibility.hideIcon(downloadCard.getImageViewStopIcon());

	}
	
	
	public void showAll() {
		iconVisibility.showIcon(downloadCard.getImageViewPauseIcon());
		iconVisibility.showIcon(downloadCard.getImageViewPlayerIcon());
	}



}
