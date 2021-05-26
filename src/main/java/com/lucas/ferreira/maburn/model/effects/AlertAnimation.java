package com.lucas.ferreira.maburn.model.effects;

import com.lucas.ferreira.maburn.controller.Alert;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AlertAnimation {
	
	private Timeline timeline = new Timeline();
	private Alert alert;
	public AlertAnimation(Alert alert) {
		// TODO Auto-generated constructor stub
		this.alert = alert;
	}
	
	public void moveAlertPaneEntered(int startY, int endY, int duration) {
		Pane pane = alert.getPane();

		
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {
			if (pane.getHeight() >= endY) {
				timeline.stop();
				return;
			}
			pane.setMinHeight(pane.getHeight() + 7);

		});

		KeyFrame keyFrameUp = new KeyFrame(Duration.seconds(duration), ev -> {
			if (pane.getHeight() <= startY) {
				timeline.stop();
				return;
			}
			pane.setMinHeight(pane.getHeight() - 8);

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
	}

	public void moveAlertPaneExited() {

	}
}
