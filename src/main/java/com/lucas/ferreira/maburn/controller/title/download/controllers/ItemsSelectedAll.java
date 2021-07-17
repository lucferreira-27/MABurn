package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class ItemsSelectedAll implements Controllers {

	private TextArea txtArea;
	private Message message;
	private boolean visible;
	private int totalItems;
	private static final String SHOW_MESSAGE = "All {value}  items selected!";

	public ItemsSelectedAll(TextArea txtArea, int totalItems) {
		this.txtArea = txtArea;
		this.totalItems = totalItems;
		message = new SucceedMessage(txtArea);
	}

	public TextArea getTextArea() {
		return txtArea;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void showMessage() {
		message.showMessage(SHOW_MESSAGE.replace("{value}", String.valueOf(totalItems)));
	}

	@Override
	public List<Node> getChildren() {
		return Arrays.asList(txtArea);
	}

	@Override
	public FetchItemType getFetchItemType() {
		return FetchItemType.ALL;
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible)
			showMessage();

		txtArea.setVisible(visible);

		this.visible = visible;

	}

	@Override
	public boolean getVisible() {
		
		return visible;
	}

}
