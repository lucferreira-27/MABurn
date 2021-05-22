package com.lucas.ferreira.maburn.model.messages;

import javafx.scene.control.TextArea;

public class WarningMessage extends Message{

	public WarningMessage(TextArea area) {
		super(area);
	}

	@Override
	protected void fillText() {

		area.setStyle("-fx-text-fill: #d8a027;");

	}


}
