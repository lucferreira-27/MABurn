package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.DefaultAnimationCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.exceptions.InitializeIconsException;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.media.EpisodeDirectoryModel;

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

	public void initialize() throws Exception{
		EpisodeCardIconVisibility episodeCardIconVisibility = new EpisodeCardIconVisibility(episodeCard);
		episodeCardIconVisibility.onDownloadState(episodeDownloadItemValues.getDownloadProgressState());
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

	private void initializeIcons() throws InitializeIconsException {
		cardIcons = new EpisodeCardIcons(episodeCard, this);
		try {
			cardIcons.initialize();
		} catch (Exception e) {
			System.err.println();
			e.printStackTrace();
			throw new InitializeIconsException();
		}
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
	@Override
	public void openFolder() {
		// TODO Auto-generated method stub
			EpisodeDirectoryModel episodeDirectoryModel = new EpisodeDirectoryModel(downloadInfo);
			episodeDirectoryModel.openFolder();

	}

	@Override
	public void openTitleMedia() {
		// TODO Auto-generated method stub
		EpisodeDirectoryModel episodeDirectoryModel = new EpisodeDirectoryModel(downloadInfo);
		episodeDirectoryModel.openFile();
	}
	private void resetValues() {
		episodeDownloadItemValues.getDownloadSpeed().set(0);
		episodeDownloadItemValues.getTimeRemain().set(0);
	}



}
