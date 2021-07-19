package com.lucas.ferreira.maburn.controller.title.download;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ContentDownloadList {

	private NavDownloadList navDownloadList;
	private List<DownloadCardFull> downloadCardFulls = new ArrayList<>();
	private CardInsert cardInsert;
	private CardRemove cardRemove;
	private Supplier<Void> onRemovedCard;
	
	public ContentDownloadList(NavDownloadList navDownloadList, VBox vBox) {
		this.navDownloadList = navDownloadList;
		cardInsert = new CardInsert(vBox);
		cardRemove = new CardRemove(vBox);
	}

	public void addCard(DownloadCardFull downloadCardFull) {
		downloadCardFulls.add(downloadCardFull);
		Node node = downloadCardFull.getNode();
		cardInsert.insertInList((String) node.getUserData(), node);
		cardInit(downloadCardFull);

	}
	public void removeCard(DownloadCardFull downloadCardFull) {
		downloadCardFulls.remove(downloadCardFull);
		Node node = downloadCardFull.getNode();
		cardRemove.removeInList((String) node.getUserData(), node);
		onRemovedCard.get();

	}
	public void removeCard(DownloadCard downloadCard) {
		
		downloadCardFulls.stream().filter((card) -> card.getCard().equals(downloadCard)).findFirst().ifPresent((find) ->{
			downloadCardFulls.remove(find);
			Node node = find.getNode();
			cardRemove.removeInList((String) node.getUserData(), node);
			onRemovedCard.get();
		});
		

	}
	public void setOnRemovedCard(Supplier<Void> onRemovedCard) {
		this.onRemovedCard = onRemovedCard;
	}
	public void cardInit(DownloadCardFull downloadCardFull) {
		new Thread(() -> {
			try {
				downloadCardFull.getCardController().start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
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
