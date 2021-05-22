package com.lucas.ferreira.maburn.model.messages;

import javafx.scene.control.TextArea;

public class SucceedMessage extends Message {

	public SucceedMessage(TextArea area) {
		super(area);
	}

	@Override
	protected void fillText() {

		area.setStyle("-fx-text-fill: #30c57b;");
		
	}

}
