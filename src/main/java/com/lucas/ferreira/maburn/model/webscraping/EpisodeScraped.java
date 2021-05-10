package com.lucas.ferreira.maburn.model.webscraping;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Definition;

public class EpisodeScraped implements ItemScraped{
	
	private Map<Definition,String> videoLinks = new HashMap<Definition,String>();
	private AnimeScraped animeScraped;
	
	public EpisodeScraped(AnimeScraped titleScraped,Map<Definition,String> videoLinks) {
		// TODO Auto-generated constructor stub
		this.animeScraped = titleScraped;
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

}
