package com.lucas.ferreira.maburn.controller.download;

import com.lucas.ferreira.maburn.controller.Controller;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadController;
import com.lucas.ferreira.maburn.model.states.RegisteredStates;

public class DownloadQueueController implements Controller {

	private DownloadQueueModal downloadQueueModal;

	public DownloadQueueController(DownloadQueueModal downloadQueueModal) {
		this.downloadQueueModal = downloadQueueModal;

	}

	@Override
	public void initialize() {
		RegisteredStates.states.forEach((k, v) -> {
			System.out.println("Key: " + k + " " + "Value: "
					+ ((TitleDownloadController) v
							.getControllerStateAdapter())
							.getTitleDownloadInitialize()
							.getTitleDownloadListCard()
							.getDownloadList()
							.getContentDownloadList()
							.getDownloadCardFulls().size());
		});

	}

}
