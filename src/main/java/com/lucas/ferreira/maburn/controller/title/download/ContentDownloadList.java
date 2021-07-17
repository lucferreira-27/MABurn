package com.lucas.ferreira.maburn.controller.title.download;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.PaneCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardState;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;

public class ContentDownloadList {
	

	private NavDownloadList navDownloadList;
	private List<DownloadCardFull> downloadCardFulls = new ArrayList<>();
	
	
	public ContentDownloadList(NavDownloadList navDownloadList) {
		this.navDownloadList = navDownloadList;
	}
	public void addCard(DownloadCardFull downloadCardFull) {
		downloadCardFulls.add(downloadCardFull);
	}
	
	
	public void updateCardsValues() {
		updateNumberOfDownloadCompletedCards();
		updateNumberOfDownloadFailedCards();
		updateNumberOfDownloadingCards();
	}


	private void updateNumberOfDownloadingCards() {
		int count = (int) downloadCardFulls.stream().filter(card -> {
			return card.getCardValues().getDownloadProgressState().get() == DownloadProgressState.DOWNLOADING;
		}).count();
		navDownloadList.getDownloadingCards().set(count);

	}
	private void updateNumberOfDownloadCompletedCards() {
		int count = (int) downloadCardFulls.stream().filter(card -> {
			return card.getCardValues().getDownloadProgressState().get() == DownloadProgressState.COMPLETED;
		}).count();
		navDownloadList.getCompletedCards().set(count);

	}
	private void updateNumberOfDownloadFailedCards() {
		int count = (int) downloadCardFulls.stream().filter(card -> {
			DownloadProgressState state = card.getCardValues().getDownloadProgressState().get();
			return state == DownloadProgressState.FAILED || state == DownloadProgressState.CANCELED;
		}).count();
		navDownloadList.getFailedCards().set(count);

	}
	
	
	
	
	
	
public List<DownloadCardFull> getDownloadCardFulls() {
	return downloadCardFulls;
}
	

}
