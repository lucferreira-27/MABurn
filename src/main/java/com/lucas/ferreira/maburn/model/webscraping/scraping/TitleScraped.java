package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public interface TitleScraped {
	
	Sites getSite();
	String getTitleUrl();
	List<String> getItemsScraped();
}
