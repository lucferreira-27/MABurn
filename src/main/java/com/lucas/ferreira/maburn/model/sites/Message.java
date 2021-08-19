package com.lucas.ferreira.maburn.model.sites;


public class Message {
	
	private MessagesTypes messageType;
	private String param;
	
	public Message(MessagesTypes messageType, String param) {
		this.messageType = messageType;
		this.param = param;
	}
	
	public MessagesTypes getMessageType() {
		return messageType;
	}
	public String getParam() {
		return param;
	}
}
