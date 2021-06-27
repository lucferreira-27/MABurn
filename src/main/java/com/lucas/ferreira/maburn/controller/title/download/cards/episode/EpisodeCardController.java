package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.effects.AnimationCard;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class EpisodeCardController implements DownloadCardController{

	private EpisodeCard episodeCard;
	private String ICON_PATH = "icons/";
	private EpisodeCardIcons cardIcons;
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
		episodeDownloadItemValues.getName().set(downloadInfo.getFilename());
		episodeDownloadItemValues.getDirectLink().set(downloadInfo.getUrl());
		episodeCardValuesBinder.binder(episodeCard, episodeDownloadItemValues);

	}
	public void initialize() {
		
		fadeInAnimation();
		
		initializeValuesCard();

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
		cardIcons = new EpisodeCardIcons(episodeCard, this);
		cardIcons.initialize();
		initDownload();
	}
	
	
	private void fadeInAnimation() {
		Platform.runLater(() ->{
			episodeCard.getBorderPaneDetails().setOpacity(0);
			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(episodeCard.getBorderPaneDetails());
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});
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
