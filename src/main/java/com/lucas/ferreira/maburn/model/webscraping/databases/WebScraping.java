package com.lucas.ferreira.maburn.model.webscraping.databases;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;

public interface WebScraping {
	
	public WebScrapingDatas fetchAll() throws WebScrapingException;
	public WebScrapingDatas fetchImages() throws WebScrapingException;
	public WebScrapingDatas fetchTextContent() throws WebScrapingException;
	public WebScrapingDatas fetchHyperLinks() throws WebScrapingException;
	public WebScrapingDatas getDatas();


	
}
