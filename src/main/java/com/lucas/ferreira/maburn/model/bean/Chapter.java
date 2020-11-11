package com.lucas.ferreira.maburn.model.bean;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.sun.javafx.scene.SubSceneHelper.SubSceneAccessor;

public class Chapter implements CollectionSubItem {
	private Manga manga;
	private String name;
	private String destination;
	private List<Page> pagesImages = new ArrayList<>();
	private int id;
	public Manga getManga() {
		return manga;
	}
	public void setManga(Manga manga) {
		this.manga = manga;
	}

	public List<Page> getPagesImages() {
		return pagesImages;
	}
	public void setPagesImages(List<Page> pagesImages) {
		this.pagesImages = pagesImages;
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
