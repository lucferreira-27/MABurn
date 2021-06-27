package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

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
	private ChapterDownload chapterDownload;
	private ChapterDownloadValues chapterDownloadValues = new ChapterDownloadValues();
	private ChapterCardIcons chapterCardIcons;
	public ChapterCardController(ChapterCard chapterCard, DownloadInfo downloadInfo) {
		this.chapterCard = chapterCard;
		this.downloadInfo = downloadInfo;
	}

	public void initDownload() {

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
	public void initialize() {

		fadeInAnimation();

		initializeValuesCard();

		AnimationCard animations = new AnimationCard(chapterCard.getBorderPaneDetails());

		animations.onPlayAnimation(pane -> {
			pane.setVisible(true);
		});

		animations.onFinishAnimation(pane -> {
			pane.setVisible(false);
		});

		chapterCard.getRoot().hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (chapterCard.getRoot().isHover()) {
				animations.showCardDetails(180, 0.0005);
			} else {
				animations.hideCardDetails(115, 0.0005);
			}
		});
		chapterCardIcons = new ChapterCardIcons(chapterCard, this);
		chapterCardIcons.initialize();
		initializeValuesCard();
		initDownload();
	}

	private void fadeInAnimation() {
		Platform.runLater(() -> {
			chapterCard.getBorderPaneDetails().setOpacity(0);
			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(chapterCard.getBorderPaneDetails());
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});
	}

	public void initializeValuesCard() {
		ChapterCardValuesBinder chapterCardValuesBinder = new ChapterCardValuesBinder();
		chapterDownloadValues.getName().set(downloadInfo.getFilename());
		chapterDownloadValues.getTarget().set(downloadInfo.getUrl());
		chapterCardValuesBinder.binder(chapterCard, chapterDownloadValues);

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
	}

}
