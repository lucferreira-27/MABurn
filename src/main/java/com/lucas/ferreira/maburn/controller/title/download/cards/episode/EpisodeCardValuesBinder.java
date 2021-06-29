package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardValuesBinder;
import com.lucas.ferreira.maburn.util.ResolutionText;

import javafx.application.Platform;

public class EpisodeCardValuesBinder extends DownloadCardValuesBinder {
	private EpisodeCard episodeCard;
	private EpisodeDownloadItemValues episodeDownloadItemValues;

	@Override
	protected void customBinder() {
		 episodeCard = (EpisodeCard) downloadCard;
		 episodeDownloadItemValues = (EpisodeDownloadItemValues) downloadValues;
		 setCardEpisodeResolution();
		 setCardEpisodeDirectLink();
		 
		 addCardEpisodeResolutionListener();
		 addCardEpisodeDirectLinkListener();
	}

	private void setCardEpisodeResolution() {
		episodeCard.getLabelVideoResoution().setText(ResolutionText.shortText(episodeDownloadItemValues.getResolution().get()));
	}

	private void addCardEpisodeResolutionListener() {
		episodeDownloadItemValues.getResolution().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> episodeCard.getLabelVideoResoution().setText(ResolutionText.shortText(newvalue)));
		});
	}
	private void setCardEpisodeDirectLink() {
		episodeCard.getLabelDownloadLink().setText(episodeDownloadItemValues.getDirectLink().get());
	}
	private void addCardEpisodeDirectLinkListener() {
		episodeDownloadItemValues.getResolution().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> episodeCard.getLabelVideoResoution().setText(ResolutionText.shortText(newvalue)));
		});
	}


}
