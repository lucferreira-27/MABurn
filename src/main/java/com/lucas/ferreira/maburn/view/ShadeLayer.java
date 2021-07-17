package com.lucas.ferreira.maburn.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ShadeLayer {
	
	
	private final AnchorPane recShade;
	private double opacity;
	
	public ShadeLayer(AnchorPane recShade) {
		
		this.recShade = recShade;
	}
	
	public void show() {
		recShade.setVisible(true);
		
	}
	public void hide() {
		recShade.setVisible(false);
	}
	
	public double getOpacity() {
		return opacity;
	}
	
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	
	public AnchorPane getRecShade() {
		return recShade;
	}
}
