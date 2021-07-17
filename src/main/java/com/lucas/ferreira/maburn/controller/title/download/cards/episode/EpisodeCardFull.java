package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;

public class EpisodeCardFull implements DownloadCardFull{

	private EpisodeCardController episodeCardController;
	private EpisodeCard episodeCard;
	private EpisodeDownloadItemValues episodeDownloadItemValues;
	@Override
	public EpisodeCardController getCardController() {
		return episodeCardController;
	}

	@Override
	public void setCardController(DownloadCardController episodeCardController) {
		this.episodeCardController = (EpisodeCardController) episodeCardController;
	}

	@Override
	public EpisodeCard getCard() {
		return episodeCard;
	}

	@Override
	public void setCard(DownloadCard episodeCard) {
		this.episodeCard = (EpisodeCard) episodeCard;
		
	}

	@Override
	public EpisodeDownloadItemValues getCardValues() {

		return episodeDownloadItemValues;
	}

	@Override
	public void setCardValues(DownloadValues episodeDownloadItemValues) {
	
		this.episodeDownloadItemValues = (EpisodeDownloadItemValues) episodeDownloadItemValues;
		
	}



}
