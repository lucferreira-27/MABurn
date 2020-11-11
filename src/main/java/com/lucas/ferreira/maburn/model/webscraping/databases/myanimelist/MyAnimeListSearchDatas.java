package com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Databases;
import com.lucas.ferreira.maburn.model.webscraping.databases.SearchDatas;

public class MyAnimeListSearchDatas implements SearchDatas {
	
	private List<String> titles = new ArrayList<>();
	private List<String> imagesSrc = new ArrayList<>();
	private List<String> hyperLinks = new ArrayList<>();
	

	@Override
	public List<String> getTitles() {
		// TODO Auto-generated method stub
		return titles;
	}

	@Override
	public List<String> getImagesSrc() {
		// TODO Auto-generated method stub
		return imagesSrc;
	}

	@Override
	public List<String> getTitlesHyperLinks() {
		// TODO Auto-generated method stub
		return hyperLinks;
	}


	@Override
	public void setTitles(List<String> titles) {
		// TODO Auto-generated method stub
		this.titles = titles;
		
	}

	@Override
	public void setImagesSrc(List<String> imagesSrc) {
		// TODO Auto-generated method stub
		this.imagesSrc = imagesSrc;
		
		
	}

	@Override
	public void setTitlesHyperLinks(List<String> hyperLinks) {
		// TODO Auto-generated method stub
		this.hyperLinks = hyperLinks;
		
	}

	@Override
	public PagesTypes getType() {
		// TODO Auto-generated method stub
		return PagesTypes.SEARCH_PAGE;
	}

	@Override
	public Databases getDatabase() {
		// TODO Auto-generated method stub
		return Databases.MY_ANIME_LIST;
	}



}
