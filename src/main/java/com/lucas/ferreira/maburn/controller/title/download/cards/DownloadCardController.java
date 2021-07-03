package com.lucas.ferreira.maburn.controller.title.download.cards;

public interface DownloadCardController {

	
	public void initialize() throws Exception;
	
	public void resume();
	public void pause();
	public void stop();
	public void openFolder();
	public void openTitleMedia();
	

}
