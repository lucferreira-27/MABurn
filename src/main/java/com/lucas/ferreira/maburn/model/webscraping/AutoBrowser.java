package com.lucas.ferreira.maburn.model.webscraping;

public interface AutoBrowser {
	
	
	public void launch();

	public TitleScraped scrapeTitle(String titleUrl);
	
	public ItemScraped scrapeItem(String itemUrl);
	
	
	public void close();
}
