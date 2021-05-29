package com.lucas.ferreira.maburn.model.effects;

import javafx.beans.property.DoubleProperty;

public interface EffectAnimation {
	
	public void play();
	public void stop();
	public DoubleProperty getProgress();
	public void setAnimation(double duration, double pass);
}
