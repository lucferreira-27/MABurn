package com.lucas.ferreira.maburn.model.effects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimationOpacityCard{
	
	
	

	private Timeline timeline = new Timeline();
	private Pane cardDetails;


	public AnimationOpacityCard(Pane cardDetails) {
		this.cardDetails = cardDetails;
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	

	public void fadeInCardBody(double start, double duration) {
		cardDetails.setOpacity(0);
		KeyFrame keyIncreseOpacity = createKeyOpacityIncrese(start, duration);
		play(keyIncreseOpacity);
	}
	public void fadeOutCardBody(double start, double duration) {
		KeyFrame keyDecreaseOpacity = createKeyOpacityDecrease(start, duration);
		play(keyDecreaseOpacity);
	}
	
	private void stop() {
		timeline.stop();
	}

	private void play(KeyFrame keyFrame) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	
	private KeyFrame createKeyOpacityIncrese(double end, double duration) {
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {

			if (cardDetails.getOpacity() >= end) {
				stop();
				return;
			}
			cardDetails.setOpacity(cardDetails.getOpacity() + 0.01);

		});
		return keyFrameDown;
	}
	private KeyFrame createKeyOpacityDecrease(double end, double duration) {
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {

			if (cardDetails.getOpacity() <= end) {
				stop();
				return;
			}
			cardDetails.setOpacity(cardDetails.getOpacity() - 0.01);

		});
		return keyFrameDown;
	}
}
