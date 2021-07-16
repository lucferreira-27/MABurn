package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class DownloadCardLoaded {
	
	private CardFXML cardFXML;
	private DownloadCard downloadCard;
	private DownloadInfo downloadInfo;
	
	public DownloadCardLoaded() {
		
	}
	
	public DownloadCard getDownloadCard() {
		return downloadCard;
	}
	public void setDownloadCard(DownloadCard downloadCard) {
		this.downloadCard = downloadCard;
	}
	
	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}
	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}
	public CardFXML getCardFXML() {
		return cardFXML;
	}
	public void setCardFXML(CardFXML cardFXML) {
		this.cardFXML = cardFXML;
	}
	
}
