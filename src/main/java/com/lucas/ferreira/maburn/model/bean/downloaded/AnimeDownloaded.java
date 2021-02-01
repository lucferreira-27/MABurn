package com.lucas.ferreira.maburn.model.bean.downloaded;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.StringUtil;

import javafx.scene.image.Image;

public class AnimeDownloaded implements CollectionItem {
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
	private WebScraping webScraping;
	private Map<String, String> titles = new LinkedHashMap<>();
	private List<EpisodeDownloaded> listEpisodes = new ArrayList<>();
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


	public List<EpisodeDownloaded> getListEpisodes() {
		return listEpisodes;
	}

	public void setListEpisodes(List<EpisodeDownloaded> listEpisodes) {
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
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public void setImage(Image image) {
		// TODO Auto-generated method stub
		this.image = image;
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
	public void setTitleDataBase(String titleDataBase) {
		// TODO Auto-generated method stub
		this.titleDataBase = titleDataBase;

	}

	@Override
	public String getTitleFileName() {
		// TODO Auto-generated method stub
		return StringUtil.stringUtilFile(titleDataBase);
	}

	@Override
	public void addSubItens(List<CollectionSubItem> subItens) {
		// TODO Auto-generated method stub
		subItens.forEach(subItem -> listEpisodes.add((EpisodeDownloaded) subItem));

	}

	@Override
	public void setListSubItens(List<CollectionSubItem> subItems) {
		List<EpisodeDownloaded> items = new ArrayList<EpisodeDownloaded>();
		subItems.forEach(subItem -> items.add((EpisodeDownloaded) subItem));
		listEpisodes = items;
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
		return ((AnimeDownloaded) obj).getTitleDataBase().equals(this.titleDataBase);
	}

	@Override
	public Collections getCollections() {
		// TODO Auto-generated method stub
		return animeCollection;
	}

	@Override
	public void setCollections(Collections collections) {
		// TODO Auto-generated method stub
		this.animeCollection = (AnimeCollection) collections;
	}

	@Override
	public WebScraping getWebScraping() {
		// TODO Auto-generated method stub
		return webScraping;
	}

	@Override
	public void setWebScraping(WebScraping webScraping) {
		// TODO Auto-generated method stub
		this.webScraping = webScraping;

	}

	@Override
	public String toString() {
		return "AnimeDownloaded [name=" + name + ", destination=" + destination + ", dataUrl=" + dataUrl
				+ ", titleDataBase=" + titleDataBase + ", titleFileName=" + titleFileName + ", imageUrl=" + imageUrl
				+ ", imageLocal=" + imageLocal + ", id=" + id + "]";
	}

	@Override
	public Map<String, String> getTitles() {
		// TODO Auto-generated method stub
		return titles;
	}

	@Override
	public void setTitles(Map<String, String> titles) {
		// TODO Auto-generated method stub
		this.titles = titles;
	}



}
