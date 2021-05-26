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
		// TODO Auto-generated constructor stub
	}

	public MangaCollection(String destination) {
		this.destination = destination;
	}

	@Override
	public List<CollectionTitle> getItens() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.activeManga = (MangaDownloaded) obj;
		activeManga.setCollections(this);
	}

	@Override
	public CollectionTitle getActualItem() {
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
	public void setItens(List<CollectionTitle> itens) {
		// TODO Auto-generated method stub
		this.listMangas = itens;
	}

}
