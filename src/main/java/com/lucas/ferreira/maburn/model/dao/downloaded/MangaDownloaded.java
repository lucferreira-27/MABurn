package com.lucas.ferreira.maburn.model.dao.downloaded;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.util.StringUtil;

import javafx.scene.image.Image;

public class MangaDownloaded implements CollectionTitle {
	private MangaCollection mangaCollection;
	private String name;
	private String hospedSite;
	private String destination;
	private Image image;
	private String imageUrl;
	private String link;
	private String dataUrl;
	private String titleDataBase;
	private String titleFileName;
	private String imageLocal;
	private Double score;
	private String date;
	private List<ChapterDownloaded> listChapters = new ArrayList<>();
	private Map<String, String> titles = new LinkedHashMap<>();
	
	private int id;

	
	public MangaDownloaded() {
		
	}
	
	public MangaDownloaded(String destination) {
		this.destination = destination;
	}
	
	public MangaCollection getMangaCollection() {
		return mangaCollection;
	}

	public void setMangaCollection(MangaCollection mangaCollection) {
		this.mangaCollection = mangaCollection;
	}

	@Override
	public String getName() {
		
		if (name == null && (destination != null && !destination.isEmpty()))
			name = destination.substring(destination.lastIndexOf("\\")  + 1);
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
	public Image getImage() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image = image;
	}
	
	@Override
	public Category getCategory() {
		return Category.MANGA;
	}

	@Override
	public String getTitleDataBase() {
		
		return titleDataBase;
	}

	@Override
	public void setTitleDataBase(String titleDataBase) {
		
		this.titleDataBase = titleDataBase;
	}
	

	@Override
	public Map<String, String> getTitles() {
		
		return titles;
	}

	@Override
	public void setTitles(Map<String, String> titles) {
		
		this.titles = titles;
	}
	
	@Override
	public String getTitleFileName() {
		
		return StringUtil.stringUtilFile(titleDataBase);

	}
	@Override
	public void addSubItens(List<CollectionItem> subItens) {
		
		subItens.forEach(subItem -> listChapters.add((ChapterDownloaded) subItem));

	}
	@Override
	public void setListSubItems(List<CollectionItem> subItems) {
		List<ChapterDownloaded> items = new ArrayList<ChapterDownloaded>();
		subItems.forEach(subItem -> items.add((ChapterDownloaded) subItem));
		listChapters = items;
	}

	public List<CollectionItem> getListSubItens() {
		List<CollectionItem> subItens = new ArrayList<>();
		listChapters.forEach(ch -> subItens.add(ch));
		return subItens;
	}

	@Override
	public Collections getCollections() {
		
		return mangaCollection;
	}

	@Override
	public void setCollections(Collections collections) {
		
		this.mangaCollection = (MangaCollection) collections;
	}
	@Override
	public Double getScore() {
		
		return score;
	}

	@Override
	public void setScore(Double score) {
		
		this.score = score;
	}

	@Override
	public String getDate() {
		
		return date;
	}

	@Override
	public void setDate(String date) {
		
		this.date = date;
	}

	@Override
	public String toString() {
		return "MangaDownloaded [name=" + name + ", destination=" + destination + ", dataUrl=" + dataUrl
				+ ", titleDataBase=" + titleDataBase + ", titleFileName=" + titleFileName + ", imageUrl=" + imageUrl
				+ ", imageLocal=" + imageLocal + ", id=" + id + "]";
	}

	@Override
	public ListItemForm toForm() {
		

		ListItemForm form = new ListItemForm();
		form.setCategory(getCategory());
		form.setDestination(getDestination());
		form.setTitleDatabase(getTitleDataBase());
		form.setDataUrl(getDataBaseUrl());
		form.setImageLocal(getImageLocal());
		form.setImageUrl(getImageUrl());
		form.setId(getId());

		return form;
	}



}
