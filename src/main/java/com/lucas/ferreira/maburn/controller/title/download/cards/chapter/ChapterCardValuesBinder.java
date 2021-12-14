package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardValuesBinder;

import javafx.application.Platform;

public class ChapterCardValuesBinder extends DownloadCardValuesBinder{
	
	
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
		chapterCard.getLabelTotalPagesDownloaded().setText(String.valueOf(chapterDownloadValues.getTotalItemsDownloaded()));
	}
	
	private void addCardChapterTotalPagesDownloadedListener() {
		chapterDownloadValues.getTotalItemsDownloaded().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> chapterCard.getLabelTotalPagesDownloaded().setText(String.valueOf(newvalue.intValue())));
		});
	}





}
