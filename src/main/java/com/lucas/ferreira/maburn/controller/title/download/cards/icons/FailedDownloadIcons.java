package com.lucas.ferreira.maburn.controller.title.download.cards.icons;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.view.CardInteractIcons;
import com.lucas.ferreira.maburn.view.IconVisibility;

public class FailedDownloadIcons implements CardInteractIcons {
	private IconVisibility iconVisibility = new IconVisibility();

	private DownloadCard downloadCard;
	private DownloadCardController controller;
	public FailedDownloadIcons(DownloadCard downloadCard, DownloadCardController controller) {
		this.downloadCard = downloadCard;
		this.controller = controller;	
	}
	@Override
	public void interactTurnOn() {
		onActiveIconRemove();
		onActiveIconRetry();
	}
	private void onActiveIconRemove() {
		Icon iconDelete = (Icon) downloadCard.getImageViewDeleteIcon().getUserData();
		iconDelete.setProperties(event -> {
			controller.remove();
		});
	}

	private void onActiveIconRetry() {
		Icon iconRefresh = (Icon) downloadCard.getImageViewRefreshIcon().getUserData();
		iconRefresh.setProperties(event -> {
			controller.refresh();
		});
	}
	@Override
	public void hideAll() {
		iconVisibility.hideIcon(downloadCard.getImageViewRefreshIcon());
		iconVisibility.hideIcon(downloadCard.getImageViewDeleteIcon());
	}


	@Override
	public void showAll() {
		iconVisibility.showIcon(downloadCard.getImageViewRefreshIcon());
		iconVisibility.showIcon(downloadCard.getImageViewDeleteIcon());
	}
	@Override
	public void disableAll() {
		
	}
	@Override
	public void enableAll() {
		
	}
}
