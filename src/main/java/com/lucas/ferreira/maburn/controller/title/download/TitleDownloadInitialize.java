package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlert;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;

public class TitleDownloadInitialize implements Initialize{

	private TitleDownload titleDownload;
	private TitleDownloadController titleDownloadController;
	private TitleDownloadControlers titleDownloadControlers;
	private Title title;
	private TitleDownloadIcons titleDownloadIcons;
	private TitleDownloadListCard titleDownloadListCard;
	private ManualSearchAlertController manualSearchAlertController;
	
	public TitleDownloadInitialize(TitleDownload titleDownload, TitleDownloadController titleDownloadController) {
		this.titleDownload = titleDownload;
		this.titleDownloadController = titleDownloadController;
	}

	public void initialize() {
		try {
			title = new Title(titleDownload);
			titleDownloadControlers = new TitleDownloadControlers(titleDownload, titleDownloadController, title);
			titleDownloadIcons = new TitleDownloadIcons(titleDownload, titleDownloadController);
			titleDownloadListCard = new TitleDownloadListCard(title, titleDownload);
			manualSearchAlertController = new ManualSearchAlertController(new ManualSearchAlert(titleDownload.getApManualSearch()));

			title.initialize();
			titleDownloadControlers.initialize();
			titleDownloadIcons.initialize();
			titleDownloadListCard.initialize();

		} catch (Exception e) {
			e.printStackTrace();
			throw new InitializeExcpetion(e.getMessage());
		}
	}

	public TitleDownload getTitleDownload() {
		return titleDownload;
	}

	public TitleDownloadController getTitleDownloadController() {
		return titleDownloadController;
	}

	public TitleDownloadControlers getTitleDownloadControlers() {
		return titleDownloadControlers;
	}

	public Title getTitle() {
		return title;
	}

	public TitleDownloadIcons getTitleDownloadIcons() {
		return titleDownloadIcons;
	}

	public TitleDownloadListCard getTitleDownloadListCard() {
		return titleDownloadListCard;
	}

	public ManualSearchAlertController getManualSearchAlertController() {
		return manualSearchAlertController;
	}



}
