package com.lucas.ferreira.maburn.model.webscraping.scraping;

public interface ItemScraped {
	
	public Exception getException();
	public TitleScraped getTitle();
	public Object getValues();
	
}
