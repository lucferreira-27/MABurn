package com.lucas.ferreira.maburn.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ShadeLayer {
	
	
	private final AnchorPane recShade;
	private double opacity;
	
	public ShadeLayer(AnchorPane recShade) {
		// TODO Auto-generated constructor stub
		this.recShade = recShade;
	}
	
	public void show() {
		recShade.setVisible(true);
		System.out.println(recShade.isVisible());
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
