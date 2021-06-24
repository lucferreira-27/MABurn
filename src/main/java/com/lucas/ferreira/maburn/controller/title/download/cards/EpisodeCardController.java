package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.effects.AnimationCard;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.application.Platform;

public class EpisodeCardController implements DownloadCardController{

	private EpisodeCard episodeCard;
	private String ICON_PATH = "icons/";

	private EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues();
	private EpisodeDownload episodeDownload;
	private DownloadInfo downloadInfo;

	public EpisodeCardController(EpisodeCard episodeCard, DownloadInfo downloadInfo) {
		this.episodeCard = episodeCard;
		this.downloadInfo = downloadInfo;
	}

	public void initDownload() {
		
		

		
		episodeDownload = new EpisodeDownload(downloadInfo, episodeDownloadItemValues);
		new Thread(() -> {

			try {
				episodeDownload.download();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}

	public void initializeValuesCard() {
		EpisodeCardValuesBinder episodeCardValuesBinder = new EpisodeCardValuesBinder();
		episodeDownloadItemValues.setDirectLink(downloadInfo.getUrl());
		episodeDownloadItemValues.setName(downloadInfo.getFilename());
		episodeCardValuesBinder.binder(episodeCard, episodeDownloadItemValues);

	}
	public void initialize() {


		fadeInAnimation();
		
		initializeValuesCard();
		initializeIcons();

		AnimationCard animations = new AnimationCard(episodeCard.getBorderPaneDetails());

		animations.onPlayAnimation(pane -> {
			pane.setVisible(true);
		});

		animations.onFinishAnimation(pane -> {
			pane.setVisible(false);
		});

		episodeCard.getRoot().hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (episodeCard.getRoot().isHover()) {
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
			episodeCard.getBorderPaneDetails().setOpacity(0);
			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(episodeCard.getBorderPaneDetails());
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});
	}
	
	public void initializeIcons() {

		Icon downloadIcon = new Icon(episodeCard.getImageViewDownloadIcon(),
				new IconConfig(ICON_PATH, Icons.DOWNLOAD_IN_CARD));
		downloadIcon.setProperties();

		Icon linkIcon = new Icon(episodeCard.getImageViewLinkIcon(), new IconConfig(ICON_PATH, Icons.LINK));
		linkIcon.setProperties();

		Icon iconPlay = new Icon(episodeCard.getImageViewPlayerIcon(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		iconPlay.setProperties(event -> {
			System.out.println("PLAY");
			resume();
			replaceIconPlayToPause();
		});

		Icon iconPause = new Icon(episodeCard.getImageViewPauseIcon(), new IconConfig(ICON_PATH, Icons.PAUSE_IN_CARD));
		iconPause.setProperties(event -> {
			System.out.println("RESUME");
			pause();
			replaceIconPauseToPlay();
		});

		Icon iconStop = new Icon(episodeCard.getImageViewStopIcon(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));
		iconStop.setProperties(event -> {
			System.out.println("STOP");
			stop();
		});
	}

	private void replaceIconPauseToPlay() {
		episodeCard.getImageViewPlayerIcon().setVisible(true);
		episodeCard.getImageViewPauseIcon().setVisible(false);
	}

	private void replaceIconPlayToPause() {
		episodeCard.getImageViewPauseIcon().setVisible(true);
		episodeCard.getImageViewPlayerIcon().setVisible(false);
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
