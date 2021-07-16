package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.model.Initialize;

public class TitleDownloadListCard  implements Initialize{

	private Title title;
	private TitleDownload titleDownload;
	private ListCards listCards;

	public TitleDownloadListCard(Title title, TitleDownload titleDownload) {
		this.title = title;
		this.titleDownload = titleDownload;
	}

	public void initialize() {
		listCards = new ListCards(title.getCollectionTitle(), titleDownload.getvBoxListDownloads());
	}

	public ListCards getListCards() {
		return listCards;
	}

	public Title getTitle() {
		return title;
	}

	public TitleDownload getTitleDownload() {
		return titleDownload;
	}
}
