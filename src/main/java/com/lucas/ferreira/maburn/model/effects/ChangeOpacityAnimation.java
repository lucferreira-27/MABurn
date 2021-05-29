package com.lucas.ferreira.maburn.model.effects;

import javafx.animation.Animation;
import javafx.animation.Interpolatable;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ChangeOpacityAnimation implements EffectAnimation {

	private final Pane pane;
	private Timeline timeline = new Timeline();
	private double opacity;
	private double start = 0;
	private double target = 1;
	private DoubleProperty progress = new SimpleDoubleProperty();

	public ChangeOpacityAnimation(Pane pane, double start, double target) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.target = target;
		this.pane = pane;

	}

	@Override
	public void play() {
		timeline.play();
	}

	@Override
	public void stop() {
		timeline.stop();

	}

	@Override
	public DoubleProperty getProgress() {
		return progress;
	}

	@Override
	public void setAnimation(double duration, double pass) {
		
		
		
		if (pass >= 1) {
			throw new RuntimeException("Pass can't be big than 1 or equal");
		}
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().clear();
		opacity = start;

		KeyFrame changeOpacity = new KeyFrame(Duration.seconds(duration), ev -> {
			if (opacity < target) {
				changeOpacity(pass);
			} else {
				timeline.stop();
			}

		});
		timeline.getKeyFrames().add(changeOpacity);
	}

	private void changeOpacity(double pass) {
		System.out.println("Opacity: " + opacity);
		System.out.println("ChangeOpacity Pass: " + pass);
		opacity += pass;
		pane.setOpacity(opacity);
		System.out.println("After Opacity: " + opacity);
		progress.set(opacity / target);
	}

	public double getOpacity() {
		return opacity;
	}

}
