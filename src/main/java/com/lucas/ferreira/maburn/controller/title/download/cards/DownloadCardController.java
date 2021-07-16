package com.lucas.ferreira.maburn.controller.title.download.cards;

public interface DownloadCardController extends CardController{

	
	public DownloadCardController initialize() throws Exception;
	
	public void resume();
	public void pause();
	public void stop();
	public void remove();
	public void refresh();
	public void openFolder();
	public void openTitleMedia();

	

}
