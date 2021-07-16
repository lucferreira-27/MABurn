package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;

public class FetchActionManual implements FetchAction {

	private ManualSearchAlertController manualSearchAlertController;

	public FetchActionManual(ManualSearchAlertController manualSearchAlertController) {
		this.manualSearchAlertController = manualSearchAlertController;
	}

	public FetchableTittle manualFetch(FetchableTittle fetchableTittle) throws Exception {

		String response = manualSearchAlertController.invokeAlert();
		if (response == null) {
			return null;
		}

		fetchableTittle.setTitleUrl(response);

		return fetchableTittle;

	}

	@Override
	public FetchableTittle action(FetchableTittle fetchableTittle) throws Exception {
		// TODO Auto-generated method stub
		return manualFetch(fetchableTittle);
	}

}
