package com.lucas.ferreira.maburn.model.bean.downloaded;

import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;

public class ChapterDownloaded implements CollectionSubItem {
	private MangaDownloaded manga;
	private String name;
	private String destination;
	private int id;
	public MangaDownloaded getManga() {
		return manga;
	}
	public void setManga(MangaDownloaded manga) {
		this.manga = manga;
	}


	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return manga;
	}
	
	
	
}
