package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public interface ItemScraped {
	
	public Exception getException();
	public TitleScraped getTitle();
	public void setTitle(TitleScraped titleScraped);
	public String getUrl();
	public void setUrl(String url);
	public Object getValues();
	public Sites getSite();
	public void setSite(Sites site);
	
	
}
