package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.ArrayList;
import java.util.List;

public class ChapterScraped implements ItemScraped {

	private List<String> pagesLinks = new ArrayList<String>();
	private MangaScraped mangaScraped;

	public ChapterScraped(List<String> pagesLinks) {
		// TODO Auto-generated constructor stub
		this.pagesLinks = pagesLinks;
	}

	@Override
	public TitleScraped getTitle() {
		// TODO Auto-generated method stub
		return mangaScraped;
	}

	public List<String> getPagesLinks() {
		return pagesLinks;
	}

	@Override
	public Object getValues() {
		// TODO Auto-generated method stub
		return pagesLinks;
	}

	@Override
	public String toString() {
		return "ChapterScraped [pagesLinks=" + pagesLinks + ", mangaScraped=" + mangaScraped + "]";
	}

}
