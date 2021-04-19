package com.lucas.ferreira.maburn.view.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProgressBarAnimate {
	
	private DoubleProperty progress = new SimpleDoubleProperty();
	
	
	public void start() {
		
	}
	
	
	public DoubleProperty getProgress() {
		return progress;
	}
	
	public void setProgress(DoubleProperty progress) {
		this.progress = progress;
	}
	
	
}
