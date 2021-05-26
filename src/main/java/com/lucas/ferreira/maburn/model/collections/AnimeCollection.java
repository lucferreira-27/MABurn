package com.lucas.ferreira.maburn.model.collections;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class AnimeCollection implements Collections {
	private List<CollectionTitle> listAnimes = new ArrayList<>();
	private String destination;
	private AnimeDownloaded activeAnime;

	public AnimeCollection(String destination) {
		// TODO Auto-generated constructor stub
		this.destination = destination;
	}
	
	public AnimeCollection() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<CollectionTitle> getItens() {
		// TODO Auto-generated method stub
		return listAnimes;
	}

	public List<CollectionTitle> getListAnimes() {
		return listAnimes;
	}

	public void setListAnimes(List<AnimeDownloaded> listAnimes) {
		if(listAnimes != null) {
			listAnimes.forEach(manga ->{
				this.listAnimes.add(manga);
			});
		}
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public void setActualItem(CollectionTitle obj) {
		// TODO Auto-generated method stub
		this.activeAnime = (AnimeDownloaded) obj;
		activeAnime.setCollections(this);
	}

	@Override
	public CollectionTitle getActualItem() {
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
	public void setItens(List<CollectionTitle> itens) {
		// TODO Auto-generated method stub
		this.listAnimes = itens;
		
	}



}
