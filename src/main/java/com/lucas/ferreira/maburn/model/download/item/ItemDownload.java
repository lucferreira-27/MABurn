package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;

public interface ItemDownload {
	public void download() throws Exception;
	public void pause();
	public void resume();
	public void stop();
	
	public void showDownloadValuesRealTimeInfo();
	public void hideDownloadValuesRealTimeInfo();

}
