package com.lucas.ferreira.maburn.model.webscraping.scraping.title;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

import javafx.scene.image.Image;

public interface TitleScraped {
	
	Sites getSite();
	String getTitleUrl();
	PageInfo getPageInfo();
	List<String> getItemsScraped();
}
