package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.DefaultAnimationCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;

public class EpisodeCardController implements DownloadCardController {

	private EpisodeCard episodeCard;
	private EpisodeCardIcons cardIcons;
	private DefaultAnimationCard defaultAnimationCard;
	private EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues();
	private EpisodeDownload episodeDownload;
	private DownloadInfo downloadInfo;

	public EpisodeCardController(EpisodeCard episodeCard, DownloadInfo downloadInfo) {
		this.episodeCard = episodeCard;
		this.downloadInfo = downloadInfo;
	}

	public void initialize() {

		initializeValuesCard();
		initializeIcons();
		initializeAnimations();
		initializeDownload();
	}

	public void initializeValuesCard() {
		EpisodeCardValuesBinder episodeCardValuesBinder = new EpisodeCardValuesBinder();
		episodeDownloadItemValues.getName().set(downloadInfo.getFilename());
		episodeDownloadItemValues.getDirectLink().set(downloadInfo.getUrl());
		episodeCardValuesBinder.binder(episodeCard, episodeDownloadItemValues);

	}

	private void initializeIcons() {
		cardIcons = new EpisodeCardIcons(episodeCard, this);
		cardIcons.initialize();
	}

	private void initializeAnimations() {
		defaultAnimationCard = new DefaultAnimationCard(episodeCard);
		defaultAnimationCard.initAnimationCard();
	}

	public void initializeDownload() {

		episodeDownload = new EpisodeDownload(downloadInfo, episodeDownloadItemValues);
		new Thread(() -> {

			try {
				episodeDownload.download();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	@Override
	public void resume() {
		episodeDownload.resume();
	}

	@Override
	public void pause() {
		episodeDownload.pause();
	}

	@Override
	public void stop() {
		episodeDownload.stop();
		resetValues();
	}

	private void resetValues() {
		episodeDownloadItemValues.getDownloadSpeed().set(0);
		episodeDownloadItemValues.getTimeRemain().set(0);
	}

}
