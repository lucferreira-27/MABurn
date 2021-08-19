package com.lucas.ferreira.maburn.model.sites;

public class ProcessMessage {

	public Message process(String message) throws Exception {
		String param = "";
		try {
			param = message.substring(message.indexOf("('") + 2, message.indexOf("')"));
		} catch (Exception e) {

			e.printStackTrace();
		}
		message = message.substring(0, message.indexOf("('"));
		MessagesTypes messagesType;

		messagesType = MessagesTypes.valueOf(message);
		
		if(param.isEmpty()) {
			throw new Exception("Message content is empty!");
		}
		
		return new Message(messagesType, param);

	}
}
