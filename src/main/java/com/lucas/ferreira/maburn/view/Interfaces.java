package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.collection.CollectionInterfaceController;
import com.lucas.ferreira.maburn.controller.download.DownloadQueueModal;
import com.lucas.ferreira.maburn.controller.home.HomeInterfaceController;
import com.lucas.ferreira.maburn.controller.home.ModelInterface;
import com.lucas.ferreira.maburn.controller.settings.SettingsModel;
import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadModel;
import com.lucas.ferreira.maburn.controller.title.search.TittleSearchInterfaceController;

public enum Interfaces {

	HOME("HomeViewFXML.fxml", new HomeInterfaceController()), TITLE("TitleViewFXML.fxml", new TitleController()),
	TITLE_SEARCH("TitleSearchViewFXML.fxml", new TittleSearchInterfaceController()),
	TITLE_DOWNLOAD("TitleDownload.fxml", new TitleDownloadModel()),

	DOWNLOADS("DownloadQueueView.fxml", new DownloadQueueModal()),
	CONFIGURATION("HelperViewFXML.fxml", new SettingsModel()), CALENDAR("", null),
	COLLECTION("ItemsViewFXML.fxml", new CollectionInterfaceController()), DOWNLOAD_CARD("DownloadCard.fxml", null);

	private String fxml;
	private final ModelInterface modelInterface;
	private Class<? extends ModelInterface> classModal;

	private Interfaces(String fxml, ModelInterface modelInterface) {
		this.modelInterface = modelInterface;
		if (modelInterface != null)
			this.classModal = modelInterface.getClass();
		this.fxml = fxml;
	}

	public ModelInterface getNewModalInterface() throws InstantiationException, IllegalAccessException {

		return classModal.newInstance();
	}

	public ModelInterface getFinalModalInterface() {

		return modelInterface;
	}

	public String getFxml() {
		return fxml;
	}
}
