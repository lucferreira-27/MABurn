package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.application.Platform;

public class ChapterCardValuesBinder extends CardValuesBinder{

	@Override
	protected void customBinder() {
		ChapterCard chapterCard = (ChapterCard) downloadCard;
		ChapterDownloadValues chapterDownloadValues = (ChapterDownloadValues) downloadValues;
		
		chapterDownloadValues.getTotalPagesDownloaded().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> chapterCard.getLabelTotalPagesDownloaded().setText(String.valueOf(newvalue.intValue())));
		});
		
	}





}
