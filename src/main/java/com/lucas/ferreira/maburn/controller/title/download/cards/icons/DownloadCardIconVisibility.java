package com.lucas.ferreira.maburn.controller.title.download.cards.icons;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.view.CardInteractIcons;
import com.lucas.ferreira.maburn.view.IconVisibility;

import javafx.beans.property.ObjectProperty;

public class DownloadCardIconVisibility {
	private DownloadCardInteractIcons downloadCardInteractIcons;

	public DownloadCardIconVisibility(DownloadCardInteractIcons downloadCardInteractIcons) {
		this.downloadCardInteractIcons = downloadCardInteractIcons;
	}

	public void onDownloadState(ObjectProperty<DownloadProgressState> downloadStateProperty) {

		downloadStateProperty.addListener((obs, oldvalue, newvalue) -> {
			switch (newvalue) {



			case COMPLETED:
				downloadCompleted();
				break;

			case CANCELED:
				downloadCanceled();
				break;
			case FAILED:
				downloadFailed();
				break;
			case WAITING:
				disableCardIcons(downloadCardInteractIcons.getPlayerIcons());
				break;
			case DOWNLOADING:
				enableCardIcons(downloadCardInteractIcons.getPlayerIcons());
				if(oldvalue == DownloadProgressState.FAILED || oldvalue == DownloadProgressState.CANCELED) {
					downloadRefreshed();
				}
				break;
			default:
				break;
			}
		});

	}

	private void hideCardIcons(CardInteractIcons cardInteractIcons) {
		cardInteractIcons.hideAll();
	}

	private void showCardIcons(CardInteractIcons cardInteractIcons) {
		cardInteractIcons.showAll();

	}

	private void disableCardIcons(CardInteractIcons cardInteractIcons) {
		cardInteractIcons.disableAll();

	}

	private void enableCardIcons(CardInteractIcons cardInteractIcons) {
		cardInteractIcons.enableAll();

	}
	
	private void downloadRefreshed() {
		showCardIcons(downloadCardInteractIcons.getPlayerIcons());
		hideCardIcons(downloadCardInteractIcons.getFailedDownloadIcons());
	}
	
	private void downloadFailed() {

		hideCardIcons(downloadCardInteractIcons.getPlayerIcons());
		showCardIcons(downloadCardInteractIcons.getFailedDownloadIcons());

	}

	private void downloadCanceled() {

		disableCardIcons(downloadCardInteractIcons.getPlayerIcons());

		downloadCardInteractIcons.getPlayerIcons().hideAll();
		downloadCardInteractIcons.getFailedDownloadIcons().showAll();

	}

	private void downloadCompleted() {

		downloadCardInteractIcons.getPlayerIcons().hideAll();
		downloadCardInteractIcons.getCompletedDownloadIcons().showAll();

	}
}
