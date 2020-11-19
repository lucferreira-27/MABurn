package com.lucas.ferreira.maburn.model.bean;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.scene.image.Image;

public class MangaDownloaded implements CollectionItem {
	private MangaCollection mangaCollection;
	private String name;
	private String hospedSite;
	private String destination;
	private Image cover;
	private String imageUrl;
	private String link;
	private String dataUrl;
	private String titleDataBase;
	private String imageLocal;
	
	private List<ChapterDownloaded> listChapters = new ArrayList<>();

	private int id;
	public MangaCollection getMangaCollection() {
		return mangaCollection;
	}
	public void setMangaCollection(MangaCollection mangaCollection) {
		this.mangaCollection = mangaCollection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<ChapterDownloaded> getListChapters() {
		return listChapters;
	}
	public void setListChapters(List<ChapterDownloaded> listChapters) {
		this.listChapters = listChapters;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDataBaseUrl() {
		return dataUrl;
	}
	public void setDataBaseUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	public String getImageLocal() {
		return imageLocal;
	}
	public void setImageLocal(String imageLocal) {
		this.imageLocal = imageLocal;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public Category getCategory() {
		return Category.MANGA;
	}
	@Override
	public String getTitleDataBase() {
		// TODO Auto-generated method stub
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
		subItens.forEach(subItem -> listChapters.add((ChapterDownloaded) subItem));
		
	}
	public List<CollectionSubItem> getListSubItens() {
		List<CollectionSubItem> subItens = new ArrayList<>();
		listChapters.forEach(ch -> subItens.add(ch));
		return subItens;
	}




		
	
	
	
	
	
	
}
