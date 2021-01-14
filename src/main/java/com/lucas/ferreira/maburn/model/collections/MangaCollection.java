package com.lucas.ferreira.maburn.model.collections;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class MangaCollection implements Collections {
	
	private List<CollectionItem> listMangas = new ArrayList<>();
	private String destination;
	private MangaDownloaded activeManga;
	
	@Override
	public List<CollectionItem> getItens() {
		// TODO Auto-generated method stub
		return listMangas;
	}
	

	
	public void setListMangas(List<MangaDownloaded> listMangas) {
		if(listMangas != null) {
			listMangas.forEach(manga ->{
				this.listMangas.add(manga);
			});
		}
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public MangaDownloaded getActiveManga() {
		return activeManga;
	}
	public void setActiveManga(MangaDownloaded activeManga) {
		this.activeManga = activeManga;
	}

	@Override
	public void setActualItem(CollectionItem obj) {
		// TODO Auto-generated method stub
	this.activeManga = (MangaDownloaded) obj;
		
	}

	@Override
	public CollectionItem getActualItem() {
		// TODO Auto-generated method stub
		if (activeManga == null && !listMangas.isEmpty()) {
			return listMangas.get(0);
		}
		return activeManga;
	}

	@Override
	public Category getCategory() {
		// TODO Auto-generated method stub
		return Category.MANGA;
	}



	@Override
	public void setItens(List<CollectionItem> itens) {
		// TODO Auto-generated method stub
		this.listMangas = itens;
	}


	
	
}
