package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardIconVisibility;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardInteractIcons;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.effects.DefaultAnimationCard;
import com.lucas.ferreira.maburn.model.media.ChapterDirecotoryModel;
import com.lucas.ferreira.maburn.model.media.EpisodeDirectoryModel;

public class ChapterCardController implements DownloadCardController {

	private ChapterCard chapterCard;
	private DownloadInfo downloadInfo;
	private DefaultAnimationCard defaultAnimationCard;
	private ChapterDownload chapterDownload;
	private ChapterDownloadValues chapterDownloadValues = new ChapterDownloadValues();
	private ChapterCardIcons chapterCardIcons;

	public ChapterCardController(ChapterCard chapterCard, DownloadInfo downloadInfo) {
		this.chapterCard = chapterCard;
		this.downloadInfo = downloadInfo;
	}

	@Override
	public DownloadCardController initialize() throws Exception{

		initializeValuesCard();
		initializeIcons();
		DownloadCardInteractIcons downloadCardInteractIcons = new DownloadCardInteractIcons(this, chapterCard);
		downloadCardInteractIcons.interactTurnOn();
		DownloadCardIconVisibility episodeCardIconVisibility = new DownloadCardIconVisibility(downloadCardInteractIcons);
		episodeCardIconVisibility.onDownloadState(chapterDownloadValues.getDownloadProgressState());
		initializeAnimations();
		initializeDownload();
		return this;
	}

	public void initializeValuesCard() {
		ChapterCardValuesBinder chapterCardValuesBinder = new ChapterCardValuesBinder();
		chapterDownloadValues.getName().set(downloadInfo.getFilename());
		chapterDownloadValues.getTarget().set(downloadInfo.getUrl());
		chapterCardValuesBinder.binder(chapterCard, chapterDownloadValues);

	}

	private void initializeIcons() {
		chapterCardIcons = new ChapterCardIcons(chapterCard, this);
		chapterCardIcons.initialize();

	}

	private void initializeAnimations() {
		defaultAnimationCard = new DefaultAnimationCard(chapterCard);
		defaultAnimationCard.initAnimationCard();
	}

	public void initializeDownload() {

		downloadInfo.getListUrls().forEach(link -> {
			PageDownloadItemValues pageDownloadItemValues = new PageDownloadItemValues();
			pageDownloadItemValues.getDirectLink().set(link);
			chapterDownloadValues.getListItemsDownloadValues().add(pageDownloadItemValues);
		});

		chapterDownload = new ChapterDownload(chapterDownloadValues, downloadInfo);
		new Thread(() -> {

			try {
				chapterDownload.download();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	@Override
	public void resume() {
		chapterDownload.resume();
	}

	@Override
	public void pause() {
		chapterDownload.pause();
	}

	@Override
	public void stop() {
		chapterDownload.stop();
		resetValues();
	}

	private void resetValues() {
		chapterDownloadValues.getDownloadSpeed().set(0);
		chapterDownloadValues.getTimeRemain().set(0);
	}

	@Override
	public void openFolder() {
		// TODO Auto-generated method stub
		ChapterDirecotoryModel chapterDirecotoryModel = new ChapterDirecotoryModel(downloadInfo, chapterDownloadValues);
		chapterDirecotoryModel.openFolder();

	}

	@Override
	public void openTitleMedia() {
		// TODO Auto-generated method stub
		ChapterDirecotoryModel chapterDirecotoryModel = new ChapterDirecotoryModel(downloadInfo, chapterDownloadValues);
		chapterDirecotoryModel.openFile();
	}

	@Override
	public void remove() {
		System.out.println("remove");
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		System.out.println("refresh");

	}

}
