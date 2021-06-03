package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardValues;

import javafx.scene.layout.VBox;

public class ListCards {
	
	private ListDownloadsCards listDownloadsCards;
	private ListFetchCards  listFetchCards;
	private final VBox vBoxListDownloads;
	
	public ListCards(VBox vBoxListDownloads) {
		this.vBoxListDownloads = vBoxListDownloads;
	 	listDownloadsCards = new ListDownloadsCards(vBoxListDownloads);
		listFetchCards = new ListFetchCards(vBoxListDownloads);
	}
	
	public void addFetchCard(FetchCardValues fetchCardValues) {
		listFetchCards.add(fetchCardValues);
	}
	private void addDownloadCard(ItemDownloadValues downloadValues) {
		listDownloadsCards.add(downloadValues);
	}
	public ListDownloadsCards getListDownloadsCards() {
		return listDownloadsCards;
	}
	public ListFetchCards getListFetchCards() {
		return listFetchCards;
	}
	public VBox getVBoxListDownloads() {
		return vBoxListDownloads;
	}
	
	
}
