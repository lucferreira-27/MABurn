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
	
	public ContentFetchList(NavDownloadList navDownloadList) {
		this.navDownloadList = navDownloadList;
	}
	
	public void addCard(FetchCardFull fetchCardControllers) {
		fetchCardsCardFulls.add(fetchCardControllers);

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
