package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.application.Platform;

public class ChapterCardValuesBinder extends CardValuesBinder{
	
	
	private ChapterCard chapterCard;
	private ChapterDownloadValues chapterDownloadValues;
	@Override
	protected void customBinder() {
		 chapterCard = (ChapterCard) downloadCard;
		 chapterDownloadValues = (ChapterDownloadValues) downloadValues;
		 
		 setCardChapterTotalPagesDownloaded();
		 addCardChapterTotalPagesDownloadedListener();
		
	}
	
	private void setCardChapterTotalPagesDownloaded() {
		chapterCard.getLabelTotalPagesDownloaded().setText(String.valueOf(chapterDownloadValues.getTotalPagesDownloaded()));
	}
	
	private void addCardChapterTotalPagesDownloadedListener() {
		chapterDownloadValues.getTotalPagesDownloaded().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> chapterCard.getLabelTotalPagesDownloaded().setText(String.valueOf(newvalue.intValue())));
		});
	}





}
