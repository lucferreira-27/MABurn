package com.lucas.ferreira.maburn.controller;

import javafx.scene.layout.Pane;

public interface Alert {
	public void show();
	
	public void hide();
	
	public boolean isVisible();
	
	public Pane getPane();
}
