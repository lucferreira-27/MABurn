package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public interface ItemScraped {
	
	public Exception getException();
	public TitleScraped getTitle();
	public void setTitle(TitleScraped titleScraped);
	public void setSiteResult(SiteResult siteResult);
	public SiteResult getSiteResult();
	public String getName();
	public RegisteredSite getRegisteredSite();
	public void setRegisteredSite(RegisteredSite site);
	
	
}
