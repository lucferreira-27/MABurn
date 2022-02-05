package com.lucas.ferreira.maburn.model.sites;

import java.util.logging.Logger;

public class SelectAction {

	private Actions actions;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public SelectAction(Actions actions) {
		this.actions = actions;
	}

	public void selectAction(Message message) {
		LOGGER.info("SELECT ACTION: " + message.getMessageType() + " PARAM: " + message.getParam());

		switch (message.getMessageType()) {
		case CLICK:
			actions.click(message.getParam());
			break;
		case RESULT:
			actions.getResult(message.getParam());
			break;
		case GOTO:
			actions.actionGoto(message.getParam());
			break;

		case SCREENSHOT:
			actions.actionScreenshot();
			break;
		default:
			break;
		}
	}
}
