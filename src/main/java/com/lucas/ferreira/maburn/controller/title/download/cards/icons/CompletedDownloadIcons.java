package com.lucas.ferreira.maburn.controller.title.download.cards.icons;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.view.CardInteractIcons;
import com.lucas.ferreira.maburn.view.IconVisibility;

public class CompletedDownloadIcons implements CardInteractIcons{

	private IconVisibility iconVisibility = new IconVisibility();
	private DownloadCard downloadCard;
	private DownloadCardController controller;
	public CompletedDownloadIcons(DownloadCard downloadCard, DownloadCardController controller) {
		this.downloadCard = downloadCard;
		this.controller = controller;
	}
	
	
	@Override
	public void interactTurnOn() {
		onActiveIconOpenFolder();
		onActiveIconTitleMedia();
	}
	
	private void onActiveIconOpenFolder() {
		Icon iconOpenFolder = (Icon) downloadCard.getImageViewOpenFolderIcon().getUserData();

		iconOpenFolder.setProperties(event -> controller.openFolder());
	}

	private void onActiveIconTitleMedia() {
		Icon iconWatch = (Icon) downloadCard.getImageViewTitleMediaIcon().getUserData();

		iconWatch.setProperties(event -> controller.openTitleMedia());
	}


	@Override
	public void hideAll() {
		iconVisibility.hideIcon(downloadCard.getImageViewTitleMediaIcon());
		iconVisibility.hideIcon(downloadCard.getImageViewOpenFolderIcon());
	}


	@Override
	public void showAll() {
		iconVisibility.showIcon(downloadCard.getImageViewTitleMediaIcon());
		iconVisibility.showIcon(downloadCard.getImageViewOpenFolderIcon());
	}


	@Override
	public void disableAll() {
		
	}


	@Override
	public void enableAll() {
		
	}

}
