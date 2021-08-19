package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

public interface TitleScraped {
	
	RegisteredSite getRegisteredSite();
	String getTitleUrl();
	PageInfo getPageInfo();
	Map<String, List<String>> getItemsScraped();
}
