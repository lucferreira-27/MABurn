package com.lucas.ferreira.maburn.controller.title.download.cards;

public enum CardFXML {
	DOWNLOAD_EPISODE_CARD("EpisodeCard.fxml"),
	DOWNLOAD_CHAPTER_CARD("ChapterCard.fxml"),
	FETCH_CARD("FetchCard.fxml");
	
	private String fxml;

	private CardFXML(String fxml) {
		this.fxml = fxml;

	}

	public String getFxml() {
		return fxml;
	}

}
