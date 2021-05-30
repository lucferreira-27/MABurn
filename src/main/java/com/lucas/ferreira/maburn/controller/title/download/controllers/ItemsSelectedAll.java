package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.messages.Message;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class ItemsSelectedAll implements Controllers {

	private TextArea txtArea;
	private Message message;
	private boolean visible;

	public ItemsSelectedAll(TextArea txtArea, Message message) {
		// TODO Auto-generated constructor stub
		this.txtArea = txtArea;
		this.message = message;

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
			message.showMessage();
		
		

				
		txtArea.setVisible(visible);
		
		this.visible = visible;

	}

	@Override
	public boolean getVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

}
