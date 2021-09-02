package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import java.util.ArrayList;
import java.util.List;

public class ChapterScraped implements ItemScraped {
	
	
	private String name;
	private List<String> pagesLinks = new ArrayList<>();
	private MangaScraped mangaScraped;
	private Exception exception;
	private RegisteredSite registeredSite;
	private SiteResult siteResult;

	public ChapterScraped(SiteResult siteResult) {
		this.pagesLinks = siteResult.getItemsValues().get(0).getUrls();
		this.name = siteResult.getItemsValues().get(0).getName();
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
	public RegisteredSite getRegisteredSite() {
		return registeredSite;
	}

	@Override
	public void setRegisteredSite(RegisteredSite registeredSite) {
		this.registeredSite = registeredSite;
	}

	@Override
	public void setSiteResult(SiteResult siteResult) {
		this.siteResult = siteResult;
		
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public SiteResult getSiteResult() {
		return siteResult;
	}

}
