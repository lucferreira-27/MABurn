package com.lucas.ferreira.maburn.model.effects;

import com.lucas.ferreira.maburn.model.alert.Alert;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.util.Duration;

public class AlertAnimation {

	private Timeline timeline = new Timeline();
	private Alert alert;
	private BooleanProperty propertyFinish = new SimpleBooleanProperty();
	private double progress = 0;

	public AlertAnimation(Alert alert) {
		this.alert = alert;
		timeline.setCycleCount(Animation.INDEFINITE);
	}

	public void moveAlertPaneExited(int endY, double duration) {

		Parent pane = alert.getParentPane();
		propertyFinish.set(false);
		progress = 0;

		int pass = 8;
		final double passProgress = passProgress(pane.getLayoutY(), 8);

		KeyFrame keyFrameUp = new KeyFrame(Duration.seconds(duration), ev -> {
			progress += passProgress;
			if (pane.getLayoutY() <= endY) {
				timeline.getKeyFrames().clear();
				timeline.stop();
				pane.setOpacity(1);
				propertyFinish.set(true);
				return;
			}

			pane.setLayoutY(pane.getLayoutY() - pass);
			pane.setOpacity(progress / 100);
		});

		timeline.getKeyFrames().add(keyFrameUp);
		timeline.play();

	}

	private double passProgress(double layoutY, int pass) {
		double x = (layoutY - pass) * 100;
		double r = 100 - (x / layoutY);
		return r;
	}

	public void moveAlertPaneEntered(int endY, double duration) {
		Parent pane = alert.getParentPane();
		propertyFinish.set(false);
		progress = 0;
		int pass = 8;
		final double passProgress = (pane.getLayoutY() / endY);
		KeyFrame keyFrameDown = new KeyFrame(Duration.seconds(duration), ev -> {
			if (pane.getLayoutY() >= endY) {
				timeline.getKeyFrames().clear();
				timeline.stop();
				propertyFinish.set(true);
				pane.setOpacity(0);
				return;
			}
			pane.setLayoutY(pane.getLayoutY() + pass);
			pane.setOpacity(pane.getOpacity() - passProgress);

		});

		timeline.getKeyFrames().add(keyFrameDown);
		timeline.play();
	}

	public BooleanProperty propertyFinish() {
		return propertyFinish;
	}
}
