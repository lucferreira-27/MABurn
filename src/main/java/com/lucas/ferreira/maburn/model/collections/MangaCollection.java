package com.lucas.ferreira.maburn.model.collections;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class MangaCollection implements Collections {

	private List<CollectionTitle> listMangas = new ArrayList<>();
	private String destination;
	private MangaDownloaded activeManga;

	public MangaCollection() {
		
	}

	public MangaCollection(String destination) {
		this.destination = destination;
	}

	@Override
	public List<CollectionTitle> getItens() {
		
		return listMangas;
	}

	public void setListMangas(List<MangaDownloaded> listMangas) {
		if (listMangas != null) {
			listMangas.forEach(manga -> {
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
		activeManga.setCollections(this);

	}

	@Override
	public void setActualItem(CollectionTitle obj) {
		
		this.activeManga = (MangaDownloaded) obj;
		activeManga.setCollections(this);
	}

	@Override
	public CollectionTitle getActualItem() {
		
		if (activeManga == null && !listMangas.isEmpty()) {
			return listMangas.get(0);
		}
		return activeManga;
	}

	@Override
	public Category getCategory() {
		
		return Category.MANGA;
	}

	@Override
	public void setItens(List<CollectionTitle> itens) {
		
		this.listMangas = itens;
	}

}
