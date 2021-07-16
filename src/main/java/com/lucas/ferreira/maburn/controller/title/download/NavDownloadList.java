package com.lucas.ferreira.maburn.controller.title.download;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NavDownloadList {
	
	
	
	private IntegerProperty totalCards = new SimpleIntegerProperty();
	private IntegerProperty downloadingCards = new SimpleIntegerProperty();
	private IntegerProperty fetchingCards = new SimpleIntegerProperty();
	private IntegerProperty completedCards = new SimpleIntegerProperty();
	private IntegerProperty failedCards = new SimpleIntegerProperty();
	private DownloadList downloadList;

	public NavDownloadList(DownloadList downloadList) {
		this.downloadList = downloadList;
	}

	
	public DownloadList getDownloadList() {
		return downloadList;
	}


	public IntegerProperty getTotalCards() {
		return totalCards;
	}


	public IntegerProperty getDownloadingCards() {
		return downloadingCards;
	}


	public IntegerProperty getFetchingCards() {
		return fetchingCards;
	}


	public IntegerProperty getCompletedCards() {
		return completedCards;
	}


	public IntegerProperty getFailedCards() {
		return failedCards;
	}

	
	
}
