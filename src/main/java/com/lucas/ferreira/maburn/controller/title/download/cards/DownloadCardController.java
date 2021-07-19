package com.lucas.ferreira.maburn.controller.title.download.cards;

public interface DownloadCardController extends CardController{

	
	public DownloadCardFull initialize();
	
	public void start()  throws Exception;
	public void resume();
	public void pause();
	public void stop();
	public void remove();
	public void refresh() throws Exception;
	public void openFolder();
	public void openTitleMedia();
	

	

}
