package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;

import javafx.scene.Node;

public class ChapterCardFull implements DownloadCardFull {
	private ChapterCardController chapterCardController;
	private ChapterCard chapterCard;
	private ChapterDownloadValues chapterDownloadValues;
	private Node node;
	@Override
	public ChapterCardController getCardController() {
		return chapterCardController;
	}

	@Override
	public void setCardController(DownloadCardController chapterCardController) {
		this.chapterCardController = (ChapterCardController) chapterCardController;
	}

	@Override
	public ChapterCard getCard() {
		return chapterCard;
	}

	@Override
	public void setCard(DownloadCard chapterCard) {
		this.chapterCard = (ChapterCard) chapterCard;
	}

	@Override
	public ChapterDownloadValues getCardValues() {
		return chapterDownloadValues;
	}

	@Override
	public void setCardValues(DownloadValues chapterDownloadValues) {
		this.chapterDownloadValues = (ChapterDownloadValues) chapterDownloadValues;
	}

	@Override
	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}



}
