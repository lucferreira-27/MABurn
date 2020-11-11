package com.lucas.ferreira.maburn.model.webscraping.databases;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Databases;

public interface SearchDatas extends WebScrapingDatas {
	
	
	
	
	public List<String> getTitles();
	public List<String> getImagesSrc();
	public List<String> getTitlesHyperLinks();
	public Databases getDatabase();
	public void setTitles(List<String> titles);
	public void setImagesSrc(List<String> imagesSrc);
	public void setTitlesHyperLinks(List<String> hyperLinks);
	
}
