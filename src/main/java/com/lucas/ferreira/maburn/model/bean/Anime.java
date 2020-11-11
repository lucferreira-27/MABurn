package com.lucas.ferreira.maburn.model.bean;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.scene.image.Image;

public class Anime implements CollectionItem {
	private AnimeCollection animeCollection;
	private String name;
	private String hospedSite;
	private String destination;
	private String link;
	private String dataUrl;
	private String titleDataBase;
	private String imageUrl;
	private String imageLocal;
	private Image cover;
	private List<Episode> listEpisodes = new ArrayList<>();
	private int id;

	public AnimeCollection getAnimeCollection() {
		return animeCollection;
	}

	public void setAnimeCollection(AnimeCollection animeCollection) {
		this.animeCollection = animeCollection;
	}

	public String getHospedSite() {
		return hospedSite;
	}

	public void setHospedSite(String hospedSite) {
		this.hospedSite = hospedSite;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Image getCover() {
		return cover;
	}

	public void setCover(Image cover) {
		this.cover = cover;
	}

	public List<Episode> getListEpisodes() {
		return listEpisodes;
	}

	public void setListEpisodes(List<Episode> listEpisodes) {
		this.listEpisodes = listEpisodes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;

	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;

	}

	@Override
	public String getLink() {
		// TODO Auto-generated method stub
		return link;
	}

	@Override
	public void setLink(String link) {
		// TODO Auto-generated method stub
		this.link = link;
	}

	@Override
	public String getDataBaseUrl() {
		// TODO Auto-generated method stub
		return dataUrl;
	}

	@Override
	public void setDataBaseUrl(String dataUrl) {
		// TODO Auto-generated method stub
		this.dataUrl = dataUrl;
	}

	@Override
	public String getImageUrl() {
		// TODO Auto-generated method stub
		return imageUrl;
	}

	@Override
	public void setImageUrl(String imageUrl) {
		// TODO Auto-generated method stub
		this.imageUrl = imageUrl;
	}

	@Override
	public String getImageLocal() {
		// TODO Auto-generated method stub
		return imageLocal;
	}

	@Override
	public void setImageLocal(String local) {
		// TODO Auto-generated method stub
		this.imageLocal = local;

	}

	@Override
	public Category getCategory() {
		// TODO Auto-generated method stub
		return Category.ANIME;
	}

	@Override
	public String getTitleDataBase() {
		return titleDataBase;
	}
	
	@Override
	public void setTitleFromDataBase(String titleDataBase) {
		// TODO Auto-generated method stub
		this.titleDataBase = titleDataBase;

	}

	@Override
	public void setListSubItens(List<CollectionSubItem> subItens) {
		// TODO Auto-generated method stub
		subItens.forEach(subItem -> listEpisodes.add((Episode) subItem));
		
	}
	
	@Override
	public List<CollectionSubItem> getListSubItens() {
		List<CollectionSubItem> subItens = new ArrayList<>();
		listEpisodes.forEach(ep -> subItens.add(ep));
		return subItens;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Anime) obj).getTitleDataBase().equals(this.titleDataBase);
	}



}
