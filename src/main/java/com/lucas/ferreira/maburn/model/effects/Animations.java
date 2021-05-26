package com.lucas.ferreira.maburn.model.effects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Animations {

	private Pane pane;
	private Rectangle rectangle = new Rectangle();
	private Timeline timeline = new Timeline();

	public Animations(Pane pane) {

		this.pane = pane;
		rectangle.setOpacity(0.5);
		pane.getChildren().add(rectangle);

	}



	public void moveMenuCollection(AnchorPane menu, int start, int end, double duration) {
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {

			if (menu.getHeight() >= end) {
				timeline.stop();
				return;
			}
			menu.setMinHeight(menu.getHeight() + 7);

		});

		KeyFrame keyFrameUp = new KeyFrame(Duration.seconds(duration), ev -> {
			if (menu.getHeight() <= start) {
				timeline.stop();
				return;
			}
			menu.setMinHeight(menu.getHeight() - 8);

		});

		timeline.setCycleCount(Animation.INDEFINITE);

		menu.setOnMouseEntered(event -> {
			timeline.stop();
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(keyFrameDown);
			timeline.play();

		});

		menu.setOnMouseExited(event -> {
			timeline.stop();
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(keyFrameUp);
			timeline.play();

		});

	}

	public Rectangle overlay(ImageView imageView) {

		Rectangle form = new Rectangle(imageView.getFitWidth() - 1, imageView.getFitHeight() - 1);
		form.setArcHeight(20);
		form.setArcWidth(20);
		imageView.setClip(form);
		rectangle.arcHeightProperty().bind(form.arcHeightProperty());
		rectangle.arcWidthProperty().bind(form.arcWidthProperty());

		final double START_SIZE = 34;
		final double END_SIZE = imageView.getFitHeight() + 2;

		rectangle.setWidth(imageView.getFitWidth() + 5);
		rectangle.setHeight(34);

		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(0.002), ev -> {

			if (rectangle.getHeight() >= END_SIZE) {
				timeline.stop();
				return;
			}
			rectangle.setHeight(rectangle.getHeight() + 2.5);

		});

		KeyFrame keyFrameUp = new KeyFrame(Duration.seconds(0.002), ev -> {
			if (rectangle.getHeight() <= START_SIZE) {
				timeline.stop();
				return;
			}
			rectangle.setHeight(rectangle.getHeight() - 2.5);

		});
		timeline.setCycleCount(Animation.INDEFINITE);

		pane.setOnMouseEntered(event -> {
			timeline.stop();
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(keyFrameDown);
			timeline.play();

		});

		pane.setOnMouseExited(event -> {
			timeline.stop();
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(keyFrameUp);
			timeline.play();

		});

		return rectangle;

	}

	public void moveOnUp() {

	}
}
