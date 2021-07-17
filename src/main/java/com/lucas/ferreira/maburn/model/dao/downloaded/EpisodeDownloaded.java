package com.lucas.ferreira.maburn.model.dao.downloaded;

import com.lucas.ferreira.maburn.model.dao.GenericItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class EpisodeDownloaded implements CollectionItem, GenericItem {
	private AnimeDownloaded anime;
	private String name;
	private String destination;
	private int id;
	public AnimeDownloaded getAnime() {
		return anime;
	}
	public void setAnime(AnimeDownloaded anime) {
		this.anime = anime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public CollectionTitle getItem() {
		
		return anime;
	}
	
	
	
	

	
	
	
}
