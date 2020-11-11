package com.lucas.ferreira.maburn.model.webscraping.databases;

public interface TitleDatas extends WebScrapingDatas {
	
	public String getTitle();
	public String getImageSrc();
	public String getHyperLink();
	public String getSynopsis();
	
	public void setTitle(String title);
	public void setImageSrc(String imageSrc);
	public void setHyperLink(String hyperLink);
	public void setSynopsis(String synopsis);

}
