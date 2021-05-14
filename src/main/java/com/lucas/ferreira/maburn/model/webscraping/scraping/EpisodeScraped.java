package com.lucas.ferreira.maburn.model.webscraping.scraping;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;

public class EpisodeScraped implements ItemScraped{
	
	private Map<Definition,String> videoLinks = new HashMap<Definition,String>();
	private AnimeScraped animeScraped;
	
	public EpisodeScraped(Map<Definition,String> videoLinks) {
		// TODO Auto-generated constructor stub
		this.videoLinks = videoLinks;
	}
	
	@Override
	public TitleScraped getTitle() {
		// TODO Auto-generated method stub
		return animeScraped;
	}
	
	public Map<Definition, String> getVideoLinks() {
		return videoLinks;
	}

	@Override
	public Object getValues() {
		// TODO Auto-generated method stub
		return videoLinks;
	}

	@Override
	public String toString() {
		return "EpisodeScraped [videoLinks=" + videoLinks + ", animeScraped=" + animeScraped + "]";
	}
	

}
