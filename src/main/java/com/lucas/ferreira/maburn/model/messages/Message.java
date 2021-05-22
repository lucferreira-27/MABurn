package com.lucas.ferreira.maburn.model.messages;

import javafx.scene.control.TextArea;

public abstract class Message {
	protected TextArea area;
	public Message(TextArea area) {
		this.area = area;
		
	}
	
	protected abstract void fillText();
	
	public void showMessage(String msg) {
		area.setVisible(true);
		area.setText(msg);
		fillText();

	}
	public void hideMessage() {
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
