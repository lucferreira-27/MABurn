package com.lucas.ferreira.maburn.model.collections;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class AnimeCollection implements Collections {
	private List<AnimeDownloaded> listAnimes = new ArrayList<>();
	private String destination;
	private AnimeDownloaded activeAnime;

	@Override
	public List<? extends CollectionItem> getItens() {
		// TODO Auto-generated method stub
		return listAnimes;
	}

	public List<AnimeDownloaded> getListAnimes() {
		return listAnimes;
	}

	public void setListAnimes(List<AnimeDownloaded> listAnimes) {
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
		this.activeAnime = (AnimeDownloaded) obj;
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
		this.listAnimes = (List<AnimeDownloaded>) itens;
		
	}

}
