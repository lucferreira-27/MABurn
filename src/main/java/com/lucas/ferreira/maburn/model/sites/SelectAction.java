package com.lucas.ferreira.maburn.model.sites;

public class SelectAction {

	private Actions actions;

	public SelectAction(Actions actions) {
		this.actions = actions;
	}

	public void selectAciton(Message message) {
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
		case ON:
			actions.javascriptIs(message.getParam());
			break;
		case SCREENSHOT:
			actions.actionScreenshot();
			break;
		default:
			break;
		}
	}
}
