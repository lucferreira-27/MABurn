package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.cards.IconVisibility;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.property.ObjectProperty;

public class ChapterCardIconVisibility {
	private IconVisibility iconVisibility = new IconVisibility();
	private ChapterCard chapterCard;

	public ChapterCardIconVisibility(ChapterCard chapterCard) {
		this.chapterCard = chapterCard;
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
		iconVisibility.hideIcon(chapterCard.getImageViewPauseIcon());
		iconVisibility.hideIcon(chapterCard.getImageViewPlayerIcon());
		iconVisibility.hideIcon(chapterCard.getImageViewStopIcon());

		iconVisibility.showIcon(chapterCard.getImageViewReadIcon());
		iconVisibility.showIcon(chapterCard.getImageViewOpenFolderIcon());

	}
}
