package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.IconVisibility;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.ObjectProperty;

public class EpisodeCardIconVisibility {
	private IconVisibility iconVisibility = new IconVisibility();
	private EpisodeCard episodeCard;

	public EpisodeCardIconVisibility(EpisodeCard episodeCard) {
		this.episodeCard = episodeCard;
	}

	public void onDownloadState(ObjectProperty<DownloadProgressState> downloadStateProperty) {

		downloadStateProperty.addListener((obs, oldvalue, newvalue) -> {
			switch (newvalue) {
				case COMPLETED:
					downloadCompleted();
					break;
	
				default:
					break;
				}
		});

	}

	private void downloadCompleted() {
		iconVisibility.hideIcon(episodeCard.getImageViewPauseIcon());
		iconVisibility.hideIcon(episodeCard.getImageViewPlayerIcon());
		iconVisibility.hideIcon(episodeCard.getImageViewStopIcon());

		iconVisibility.showIcon(episodeCard.getImageViewWatchIcon());
		iconVisibility.showIcon(episodeCard.getImageViewOpenFolderIcon());

	}
}
