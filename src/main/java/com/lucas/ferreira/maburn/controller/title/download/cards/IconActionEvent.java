package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.util.Icon;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class IconActionEvent {
	 
	public static void setActionEvent(Icon icon, EventHandler<? super MouseEvent> event) {
		icon.setProperties(event);
	}
	
}
