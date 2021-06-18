package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class ChapterCardController implements DownloadCardController {

	private ChapterDownloadValues chapterDownloadValues;
	private DownloadInfo downloadInfo;
	
	public ChapterCardController(ChapterDownloadValues chapterDownloadValues, DownloadInfo downloadInfo) {
		this.chapterDownloadValues = chapterDownloadValues;
		this.downloadInfo = downloadInfo;
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
