package com.lucas.ferreira.maburn.model.alert;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public interface Alert {
	public void show();
	
	public void hide();
	
	public boolean isVisible();
	

	Parent getParentPane();
	
	Pane getPane();
}
