package com.lucas.ferreira.maburn.model.alert;

public class ManualSearchAlertController {
	
	
	private ManualSearchAlert manualSearchAlert;
	
	
	public ManualSearchAlertController(ManualSearchAlert manualSearchAlert) {

		this.manualSearchAlert = manualSearchAlert;
	}
	

	public String invokeAlert() {
		manualSearchAlert.show();
		String response  = manualSearchAlert.waitAnswear();
		manualSearchAlert.hide();
		return response;
	}
}
