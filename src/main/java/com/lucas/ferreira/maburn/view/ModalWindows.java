package com.lucas.ferreira.maburn.view;

public enum ModalWindows {
	
	INFO_WINDOW("fxml/ModalAlertWindow.fxml"),
	ERROR_WINDOW("fxml/ModalErrorWindow.fxml"),
	QUESTION_WINDOW("fxml/ModalQuestionWindow.fxml");
	private String fxml;
	
	ModalWindows(String fxml) {
		this.fxml = fxml;
	}

	public String getFxml() {
		return fxml;
	}
}
