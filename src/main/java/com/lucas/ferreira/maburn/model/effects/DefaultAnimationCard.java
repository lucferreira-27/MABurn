package com.lucas.ferreira.maburn.model.effects;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;

import javafx.application.Platform;

public class DefaultAnimationCard {

	private DownloadCard downloadCard;
	private AnimationCard animationCard;

	public DefaultAnimationCard(DownloadCard downloadCard) {
		this.downloadCard = downloadCard;
	}

	public void initAnimationCard() {
		animationCard = new AnimationCard(downloadCard.getBorderPaneDetails());
		consumers();
		onMouseHover();
	}
	private void consumers() {
		animationCard.onPlayAnimation(pane -> {
			pane.setVisible(true);
		});

		animationCard.onFinishAnimation(pane -> {
			pane.setVisible(false);
		});
	}
	private void onMouseHover() {
		downloadCard.getRoot().hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (downloadCard.getRoot().isHover()) {
				show();
			} else {
				hide();
			}
		});
	}

	private void show() {
		animationCard.showCardDetails(180, 0.0005);

	}
	
//	public void fadeInAnimation() {
//		Platform.runLater(() -> {
//			downloadCard.getBorderPaneDetails().setOpacity(0);
//			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(downloadCard.getBorderPaneDetails());
//			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
//		});
//	}
//	public void fadeOutAnimation() {
//		Platform.runLater(() -> {
//			downloadCard.getBorderPaneDetails().setOpacity(0);
//			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(downloadCard.getBorderPaneDetails());
//			animationOpacityCard.fadeOutCardBody(1, 0.5 / 100);
//		});
//	}
	private void hide() {
		animationCard.hideCardDetails(115, 0.0005);

	}


}
