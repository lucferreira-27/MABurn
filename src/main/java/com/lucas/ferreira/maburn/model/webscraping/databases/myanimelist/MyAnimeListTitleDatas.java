package com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist;

import com.lucas.ferreira.maburn.model.webscraping.databases.TitleDatas;

public class MyAnimeListTitleDatas implements TitleDatas {
	
	private String title;
	private String synopsis;
	private String score;
	private String imageCover;
	private String hyperLink;
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	@Override
	public String getImageSrc() {
		// TODO Auto-generated method stub
		return imageCover;
	}
	@Override
	public String getHyperLink() {
		// TODO Auto-generated method stub
		return hyperLink;
	}
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
		
	}

	@Override
	public void setImageSrc(String imageSrc) {
		// TODO Auto-generated method stub
		this.imageCover = imageSrc;
	}
	@Override
	public void setHyperLink(String hyperLink) {
		// TODO Auto-generated method stub
		this.hyperLink = hyperLink;
		
	}
	@Override
	public String getSynopsis() {
		return synopsis;
	}
	@Override
	public void setSynopsis(String synopsis) {
		// TODO Auto-generated method stub
		this.synopsis = synopsis;
	}
	@Override
	public PagesTypes getType() {
		// TODO Auto-generated method stub
		return PagesTypes.TITLE_PAGE;
	}
	@Override
	public String toString() {
		return "MyAnimeListTitleDatas [title=" + title + ", synopsis=" + synopsis + ", score=" + score + ", imageCover="
				+ imageCover + ", hyperLink=" + hyperLink + "]";
	}
	
	


	
}
