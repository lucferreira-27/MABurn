package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class EpisodeScraped implements ItemScraped{
	
	private Map<Definition,String> videoLinks = new HashMap<Definition,String>();
	private AnimeScraped animeScraped;
	private Exception exception;
	public EpisodeScraped(Map<Definition,String> videoLinks) {
		this.videoLinks = videoLinks;
	}
	
	public EpisodeScraped(Exception exception) {

		this.exception = exception;
	}

	@Override
	public TitleScraped getTitle() {

		return animeScraped;
	}
	
	public Map<Definition, String> getVideoLinks() {
		return videoLinks;
	}

	@Override
	public Object getValues() {

		return videoLinks;
	}

	@Override
	public String toString() {
		return "EpisodeScraped [videoLinks=" + videoLinks + ", animeScraped=" + animeScraped + "]";
	}

	@Override
	public Exception getException() {

		return exception;
	}
	

}
