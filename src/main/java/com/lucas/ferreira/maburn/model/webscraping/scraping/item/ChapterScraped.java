package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class ChapterScraped implements ItemScraped {

	private List<String> pagesLinks = new ArrayList<String>();
	private MangaScraped mangaScraped;
	private Exception exception;
	private Sites sites;
	private String url;
	
	public ChapterScraped(List<String> pagesLinks) {
		this.pagesLinks = pagesLinks;
	}
	public ChapterScraped(Exception exception) {
		this.exception = exception;
	}
	@Override
	public TitleScraped getTitle() {
		return mangaScraped;
	}

	public List<String> getPagesLinks() {
		return pagesLinks;
	}

	@Override
	public Object getValues() {
		return pagesLinks;
	}

	@Override
	public String toString() {
		return "ChapterScraped [pagesLinks=" + pagesLinks + ", mangaScraped=" + mangaScraped + "]";
	}

	@Override
	public Exception getException() {
		return exception;
	}
	@Override
	public void setTitle(TitleScraped titleScraped) {
		mangaScraped = (MangaScraped) titleScraped;
		
	}
	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return sites;
	}
	@Override
	public void setSite(Sites site) {
		// TODO Auto-generated method stub
		this.sites = site;
		
	}
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}
	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}

}
