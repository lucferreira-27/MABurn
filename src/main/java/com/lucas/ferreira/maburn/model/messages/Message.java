package com.lucas.ferreira.maburn.model.messages;

import javafx.scene.control.TextArea;

public abstract class Message {
	protected TextArea area;

	public Message(TextArea area) {
		this.area = area;
		fillText();

	}

	protected abstract void fillText();
	
	public void showMessage(String msg) {
		area.setVisible(true);
		area.setText(msg);

	}

	public void showMessage() {
		if (area.getText() != null && !area.getText().isEmpty()) {
			area.setVisible(true);
		}

	}

	public void setMessageText(String msg) {
		area.setText(msg);
	}
	
	public void hideMessage() {
		area.setVisible(false);
	}
	public void hideAndClearMessage() {
		area.setVisible(false);
		area.setText("");
	}

	public TextArea getArea() {
		return area;
	}

	public boolean isShowing() {
		// TODO Auto-generated method stub
		return area.isVisible();
	}

}
