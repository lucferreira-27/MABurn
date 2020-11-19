package com.lucas.ferreira.maburn.model.bean;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class EpisodeDownloaded implements CollectionSubItem {
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
	public CollectionItem getItem() {
		// TODO Auto-generated method stub
		return anime;
	}
	
	
	
	

	
	
	
}
