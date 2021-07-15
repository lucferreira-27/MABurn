package com.lucas.ferreira.maburn.controller.title.download;

import javafx.scene.layout.VBox;

public class DownloadList {
	
	private VBox vBoxContent;
	
	public DownloadList(VBox vBoxContent) {
		this.vBoxContent = vBoxContent;
	}
	
	public VBox getvBoxContent() {
		return vBoxContent;
	}
	
	
	private NavDownloadList navDownloadList;
	private ContentDownloadList contentDownloadList;
}
