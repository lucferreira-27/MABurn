package com.lucas.ferreira.maburn.model.effects;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;

import javafx.scene.layout.AnchorPane;

public class DefaultAnimationCard {

	private DownloadCard downloadCard;
	private AnimationCard animationCard;
	private  static final int INCREASE_VALUE = 60;
	private  static final int DECREASE_VALUE = 10;

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

		animationCard.showCardDetails((int) downloadCard.getBorderPaneMain().getHeight() + INCREASE_VALUE, 0.0005);

	}

	private void hide() {
		animationCard.hideCardDetails((int) downloadCard.getBorderPaneMain().getHeight() -  DECREASE_VALUE, 0.0005);

	}

}
