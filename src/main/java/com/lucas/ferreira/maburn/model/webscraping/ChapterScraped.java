package com.lucas.ferreira.maburn.model.webscraping;

import java.util.ArrayList;
import java.util.List;

public class ChapterScraped implements ItemScraped {

	private List<String> pagesLinks = new ArrayList<String>();
	private MangaScraped mangaScraped;

	public ChapterScraped(MangaScraped mangaScraped, List<String> pagesLinks) {
		// TODO Auto-generated constructor stub
		this.pagesLinks = pagesLinks;
		this.mangaScraped = mangaScraped;
	}

	@Override
	public TitleScraped getTitle() {
		// TODO Auto-generated method stub
		return mangaScraped;
	}

	public List<String> getPagesLinks() {
		return pagesLinks;
	}

}
