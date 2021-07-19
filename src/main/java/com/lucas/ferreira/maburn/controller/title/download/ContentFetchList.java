package com.lucas.ferreira.maburn.controller.title.download;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.CardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.PaneCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardState;

import javafx.scene.layout.VBox;

public class ContentFetchList {

	private NavDownloadList navDownloadList;
	private List<FetchCardFull> fetchCardsCardFulls = new ArrayList<FetchCardFull>();
	private CardInsert cardInsert;

	public ContentFetchList(NavDownloadList navDownloadList, VBox vbox) {
		this.navDownloadList = navDownloadList;
		cardInsert = new CardInsert(vbox);
	}
	
	public void addCard(FetchCardFull fetchCardFull) {
		fetchCardsCardFulls.add(fetchCardFull);
		cardInsert.insertInList(fetchCardFull.getCardValues().getItemName(), fetchCardFull.getNode());
		showCard(fetchCardFull);
	}
	private void showCard(FetchCardFull fetchCardFull) {
		fetchCardFull.getCardController().show();
	}
	
	public void removeCard(FetchCardFull fetchCardFull) {
		hideCard(fetchCardFull);
		fetchCardsCardFulls.remove(fetchCardFull);
	}
	private void hideCard(FetchCardFull fetchCardFull) {
		fetchCardFull.getCardController().hide();
	}
	public List<FetchCardFull> getFetchCardsCardFulls() {
		return fetchCardsCardFulls;
	}
	
	public void updateCardsValues() {
		updateNumberOfFetchingCards();
	}
	
	private void updateNumberOfFetchingCards() {
		int count = (int) fetchCardsCardFulls.stream().filter(card -> {
			return card.getCardValues().getFetchCardState().get() != FetchCardState.READY;
		}).count();
		navDownloadList.getFetchingCards().set(count);

	}



}
