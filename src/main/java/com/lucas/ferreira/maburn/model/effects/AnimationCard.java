package com.lucas.ferreira.maburn.model.effects;

import java.util.function.Consumer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimationCard {

	private Timeline timeline = new Timeline();

	private Pane cardDetails;
	private Consumer<Pane> onPlayConsumer;
	private Consumer<Pane> onFinishConsumer;

	public AnimationCard(Pane cardDetails) {
		this.cardDetails = cardDetails;
		timeline.setCycleCount(Animation.INDEFINITE);

	}

	public void hideCardDetails(int start, double duration) {
		KeyFrame keyFrameUp = createKeyFrameUp(start, duration);
		play(keyFrameUp);
	}

	public void showCardDetails(int end, double duration) {
		KeyFrame keyFrameDown = createKeyFrameDown(end, duration);
		play(keyFrameDown);

	}

	private KeyFrame createKeyFrameUp(int start, double duration) {
		KeyFrame keyFrameUp = new KeyFrame(Duration.seconds(duration), ev -> {
			if (cardDetails.getHeight() <= start) {
				stopAndConsumer();
				return;
			}
			cardDetails.setMinHeight(cardDetails.getHeight() - 2);

		});
		return keyFrameUp;
	}

	private KeyFrame createKeyFrameDown(int end, double duration) {
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {

			if (cardDetails.getHeight() >= end) {
				stop();
				return;
			}
			System.out.println(cardDetails.getMinHeight());
			cardDetails.setMinHeight(cardDetails.getHeight() + 1.5);

		});
		return keyFrameDown;
	}

	private void stopAndConsumer() {
		timeline.stop();
		consumer(onFinishConsumer);

	}

	private void stop() {
		timeline.stop();

	}

	private void play(KeyFrame keyFrame) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(keyFrame);
		consumer(onPlayConsumer);
		timeline.play();
	}

	public void onFinishAnimation(Consumer<Pane> consumer) {
		onFinishConsumer = consumer;
	}

	public void onPlayAnimation(Consumer<Pane> consumer) {
		onPlayConsumer = consumer;
	}
	private void consumer(Consumer<Pane> consumer) {
		if(consumer != null)
			consumer.accept(cardDetails);
	}

}
