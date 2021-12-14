package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.ContentDownloadList;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardIconVisibility;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardInteractIcons;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.model.DeleteFile;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.effects.DefaultAnimationCard;
import com.lucas.ferreira.maburn.model.media.ChapterDirecotoryModel;

public class ChapterCardController implements DownloadCardController {

	private ChapterCard chapterCard;
	private DownloadInfo downloadInfo;
	private DefaultAnimationCard defaultAnimationCard;
	private ChapterDownload chapterDownload;
	private ChapterDownloadValues chapterDownloadValues = new ChapterDownloadValues();
	private ChapterCardIcons chapterCardIcons;
	private ContentDownloadList contentDownloadList;
	
	public ChapterCardController(ChapterCard chapterCard, DownloadInfo downloadInfo, ContentDownloadList contentDownloadList) {
		this.chapterCard = chapterCard;
		this.downloadInfo = downloadInfo;
		this.contentDownloadList = contentDownloadList;
	}

	@Override
	public DownloadCardFull initialize() {
		
		DownloadCardFull downloadCardFull = new ChapterCardFull();
		downloadCardFull.setCard(chapterCard);
		downloadCardFull.setCardController(this);
		downloadCardFull.setCardValues(chapterDownloadValues);
		
		initializeValuesCard();
		initializeIcons();
		DownloadCardInteractIcons downloadCardInteractIcons = new DownloadCardInteractIcons(this, chapterCard);
		downloadCardInteractIcons.interactTurnOn();
		DownloadCardIconVisibility episodeCardIconVisibility = new DownloadCardIconVisibility(downloadCardInteractIcons);
		episodeCardIconVisibility.onDownloadState(chapterDownloadValues.getDownloadProgressState());
		initializeAnimations();
		return downloadCardFull;
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

	public void initializeDownload() throws ChapterDownloadException {

		downloadInfo.getListUrls().forEach(link -> {
			PageDownloadItemValues pageDownloadItemValues = new PageDownloadItemValues();
			pageDownloadItemValues.getDirectLink().set(link);
			chapterDownloadValues.getListItemsDownloadValues().add(pageDownloadItemValues);
		});

		chapterDownload = new ChapterDownload(chapterDownloadValues, downloadInfo);
		chapterDownload.download();

	}
	@Override
	public void start() throws Exception {
		initializeDownload();

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
		
		new Thread(() -> {
			chapterDownload.stop();
			resetValues();
		}).start();
	}

	private void resetValues() {
		chapterDownloadValues.getDownloadSpeed().set(0);
		chapterDownloadValues.getTimeRemain().set(0);
	}

	@Override
	public void openFolder() {
		
		ChapterDirecotoryModel chapterDirecotoryModel = new ChapterDirecotoryModel(downloadInfo, chapterDownloadValues);
		chapterDirecotoryModel.openFolder();

	}

	@Override
	public void openTitleMedia() {
		
		ChapterDirecotoryModel chapterDirecotoryModel = new ChapterDirecotoryModel(downloadInfo, chapterDownloadValues);
		chapterDirecotoryModel.openFile();
	}

	@Override
	public void remove() {
		contentDownloadList.removeCard(chapterCard);
		String filePath = downloadInfo.getRoot() + "\\" + downloadInfo.getFilename() + "."
				+ downloadInfo.getPrefFiletype().getName();
		DeleteFile deleteFile = new DeleteFile();
		deleteFile.delete(filePath);
	}

	@Override
	public void refresh() throws Exception {
		initializeDownload();
		

	}



}
