package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.effects.AnimationCard;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.application.Platform;

public class ChapterCardController implements DownloadCardController {

	private ChapterCard chapterCard;
	private DownloadInfo downloadInfo;
	private ChapterDownload chapterDownload;
	private ChapterDownloadValues chapterDownloadValues = new ChapterDownloadValues();
	private String ICON_PATH = "icons/";

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}
	
	
	@Override
	public void initialize() {


		fadeInAnimation();
		
		initializeValuesCard();
		initializeIcons();

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
		initializeValuesCard();
		initializeIcons();
		initDownload();
	}
	private void fadeInAnimation() {
		Platform.runLater(() ->{
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
	
	public void initializeIcons() {

		Icon downloadIcon = new Icon(chapterCard.getImageViewDownloadIcon(),
				new IconConfig(ICON_PATH, Icons.DOWNLOAD_IN_CARD));
		downloadIcon.setProperties();

		Icon linkIcon = new Icon(chapterCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		linkIcon.setProperties();

		Icon iconPlay = new Icon(chapterCard.getImageViewPlayerIcon(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		iconPlay.setProperties(event -> {
			System.out.println("PLAY");
			resume();
			replaceIconPlayToPause();
		});

		Icon iconPause = new Icon(chapterCard.getImageViewPauseIcon(), new IconConfig(ICON_PATH, Icons.PAUSE_IN_CARD));
		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			pause();
			replaceIconPauseToPlay();
		});

		Icon iconStop = new Icon(chapterCard.getImageViewStopIcon(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));
		iconStop.setProperties(event -> {
			System.out.println("STOP");
			stop();
		});
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


	

	private void replaceIconPauseToPlay() {
		chapterCard.getImageViewPlayerIcon().setVisible(true);
		chapterCard.getImageViewPauseIcon().setVisible(false);
	}

	private void replaceIconPlayToPause() {
		chapterCard.getImageViewPauseIcon().setVisible(true);
		chapterCard.getImageViewPlayerIcon().setVisible(false);
	}

}
