package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import com.lucas.ferreira.maburn.controller.title.download.cards.DefaultAnimationCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.effects.AnimationCard;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

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
	public void initialize() {
		initializeValuesCard();
		initializeIcons();
		initializeAnimations();
		initializeDownload();

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

}
