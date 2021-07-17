package com.lucas.ferreira.maburn.model.dao.downloaded;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.util.StringUtil;

import javafx.scene.image.Image;

public class AnimeDownloaded implements CollectionTitle {
	private AnimeCollection animeCollection;
	private String name;
	private String hospedSite;
	private String destination;
	private String link;
	private String dataUrl;
	private String titleDataBase;
	private String titleFileName;
	private String imageUrl;
	private String imageLocal;
	private Image image;
	private Double score;
	private String date;
	private Map<String, String> titles = new LinkedHashMap<>();
	private List<EpisodeDownloaded> listEpisodes = new ArrayList<>();
	private int id;

	public AnimeDownloaded(String destination) {
		
		this.destination = destination;
	}

	public AnimeDownloaded() {
		
	}

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

	public List<EpisodeDownloaded> getListEpisodes() {
		return listEpisodes;
	}

	public void setListEpisodes(List<EpisodeDownloaded> listEpisodes) {
		this.listEpisodes = listEpisodes;
	}

	@Override
	public String getName() {
		
		if (name == null && (destination != null && !destination.isEmpty()))
			name = destination.substring(destination.lastIndexOf("\\") + 1);
		return name;
	}

	@Override
	public void setName(String name) {
		
		this.name = name;

	}

	@Override
	public int getId() {
		
		return id;
	}

	@Override
	public void setId(int id) {
		
		this.id = id;

	}

	@Override
	public String getLink() {
		
		return link;
	}

	@Override
	public void setLink(String link) {
		
		this.link = link;
	}

	@Override
	public String getDataBaseUrl() {
		
		return dataUrl;
	}

	@Override
	public void setDataBaseUrl(String dataUrl) {
		
		this.dataUrl = dataUrl;
	}

	@Override
	public String getImageUrl() {
		
		return imageUrl;
	}

	@Override
	public void setImageUrl(String imageUrl) {
		
		this.imageUrl = imageUrl;
	}

	@Override
	public String getImageLocal() {
		
		return imageLocal;
	}

	@Override
	public void setImageLocal(String local) {
		
		this.imageLocal = local;

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
		
		return Category.ANIME;
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
	public String getTitleFileName() {
		
		return StringUtil.stringUtilFile(titleDataBase);
	}

	@Override
	public void addSubItens(List<CollectionItem> subItens) {
		
		subItens.forEach(subItem -> listEpisodes.add((EpisodeDownloaded) subItem));

	}

	@Override
	public void setListSubItems(List<CollectionItem> subItems) {
		List<EpisodeDownloaded> items = new ArrayList<EpisodeDownloaded>();
		subItems.forEach(subItem -> items.add((EpisodeDownloaded) subItem));
		listEpisodes = items;
	}

	@Override
	public List<CollectionItem> getListSubItens() {
		List<CollectionItem> subItens = new ArrayList<>();
		listEpisodes.forEach(ep -> subItens.add(ep));
		return subItens;
	}

	@Override
	public boolean equals(Object obj) {
		
		return ((AnimeDownloaded) obj).getTitleDataBase().equals(this.titleDataBase);
	}

	@Override
	public Collections getCollections() {
		
		return animeCollection;
	}

	@Override
	public void setCollections(Collections collections) {
		
		this.animeCollection = (AnimeCollection) collections;
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
//	@Override
//	public WebScraping getWebScraping() {
//		
//		return webScraping;
//	}
//
//	@Override
//	public void setWebScraping(WebScraping webScraping) {
//		
//		this.webScraping = webScraping;
//
//	}

	@Override
	public String toString() {
		return "AnimeDownloaded [name=" + name + ", destination=" + destination + ", dataUrl=" + dataUrl
				+ ", titleDataBase=" + titleDataBase + ", titleFileName=" + titleFileName + ", imageUrl=" + imageUrl
				+ ", imageLocal=" + imageLocal + ", id=" + id + "]";
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
