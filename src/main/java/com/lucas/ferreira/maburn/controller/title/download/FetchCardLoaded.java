package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;

public class FetchCardLoaded {
	private FetchCard fetchCard;
	private FetchCardValues fetchCardValues;
	
	public FetchCard getFetchCard() {
		return fetchCard;
	}
	public void setFetchCard(FetchCard fetchCard) {
		this.fetchCard = fetchCard;
	}
	public FetchCardValues getFetchCardValues() {
		return fetchCardValues;
	}
	public void setFetchCardValues(FetchCardValues fetchCardValues) {
		this.fetchCardValues = fetchCardValues;
	}
}
