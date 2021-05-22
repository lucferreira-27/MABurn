package com.lucas.ferreira.maburn.model.messages;

import javafx.scene.control.TextArea;

public class ErrorMessage extends Message{
	
	public ErrorMessage(TextArea area) {
		super(area);
	}

	@Override
	protected void fillText() {
		// TODO Auto-generated method stub
		area.setStyle("-fx-text-fill: #e13535;");
		System.out.println(area.getStyle());
	}


	

}
