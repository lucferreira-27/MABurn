package com.lucas.ferreira.maburn.controller.title.download;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.PaneCard;

import javafx.scene.layout.VBox;

public class ContentDownloadList implements ContentList{
	
	private List<DownloadCard> downloadCards = new ArrayList<DownloadCard>();
	private List<DownloadCardController> downloadCardControllers = new ArrayList<>();
	public ContentDownloadList() {
	}
	public void addCard(CardController cardController, PaneCard paneCard) {
		downloadCardControllers.add((DownloadCardController) cardController);
		downloadCards.add((DownloadCard) paneCard);
	}
	

}
