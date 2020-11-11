package com.lucas.ferreira.maburn.model.collections;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class AnimeCollection implements Collections {
	private List<Anime> listAnimes = new ArrayList<>();
	private String destination;
	private Anime activeAnime;

	@Override
	public List<? extends CollectionItem> getItens() {
		// TODO Auto-generated method stub
		return listAnimes;
	}

	public List<Anime> getListAnimes() {
		return listAnimes;
	}

	public void setListAnimes(List<Anime> listAnimes) {
		this.listAnimes = listAnimes;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public void setActualItem(CollectionItem obj) {
		// TODO Auto-generated method stub
		this.activeAnime = (Anime) obj;
	}

	@Override
	public CollectionItem getActualItem() {
		// TODO Auto-generated method stub
		if (activeAnime == null && !listAnimes.isEmpty()) {
			return listAnimes.get(0);
		}
		return activeAnime;
	}

	@Override
	public Category getCategory() {
		// TODO Auto-generated method stub
		return Category.ANIME;
	}

	@Override
	public void setItens(List<? extends CollectionItem> itens) {
		// TODO Auto-generated method stub
		this.listAnimes = (List<Anime>) itens;
		
	}

}
