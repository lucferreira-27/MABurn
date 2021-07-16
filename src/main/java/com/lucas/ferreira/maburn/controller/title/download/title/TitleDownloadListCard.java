package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.controller.title.download.DownloadList;
import com.lucas.ferreira.maburn.controller.title.download.ListCards;
import com.lucas.ferreira.maburn.model.Initialize;

public class TitleDownloadListCard  implements Initialize{

	private Title title;
	private TitleDownload titleDownload;
	private ListCards listCards;
	private DownloadList downloadList;
	
	public TitleDownloadListCard(Title title, TitleDownload titleDownload) {
		this.title = title;
		this.titleDownload = titleDownload;
	}

	public void initialize() {
		//listCards = new ListCards(title.getCollectionTitle(), titleDownload.getvBoxListDownloads());
		downloadList = new DownloadList(title);
	}

//	public ListCards getListCards() {
//		return listCards;
//	}
	public DownloadList getDownloadList() {
		return downloadList;
	}
	
	public Title getTitle() {
		return title;
	}

}
