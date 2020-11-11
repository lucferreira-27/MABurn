package com.lucas.ferreira.maburn.model.webscraping.databases.kitsu;

import com.lucas.ferreira.maburn.model.webscraping.databases.TitleDatas;
import com.lucas.ferreira.maburn.model.webscraping.databases.WebScrapingDatas;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.PagesTypes;

public class KitsuTitleDatas implements TitleDatas {
	private String title;
	private String synopsis;
	private String score;
	private String imageCover;
	private String hyperLink;
	@Override
	public PagesTypes getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getImageSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHyperLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSynopsis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
	}

	@Override
	public void setImageSrc(String imageSrc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHyperLink(String hyperLink) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSynopsis(String synopsis) {
		// TODO Auto-generated method stub
		
	}

}
