package com.lucas.ferreira.maburn.model.bean.downloaded;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.StringUtil;

import javafx.scene.image.Image;

public class MangaDownloaded implements CollectionItem {
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
	private WebScraping webScraping;

	private List<ChapterDownloaded> listChapters = new ArrayList<>();
	private Map<String, String> titles = new LinkedHashMap<>();
	
	private int id;

	
	public MangaDownloaded() {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		if (name == null && (destination != null && !destination.isEmpty()))
			name = destination.substring(destination.lastIndexOf("\\"));
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
		// TODO Auto-generated method stub
		return titleDataBase;
	}

	@Override
	public void setTitleDataBase(String titleDataBase) {
		// TODO Auto-generated method stub
		this.titleDataBase = titleDataBase;
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
	
	@Override
	public String getTitleFileName() {
		// TODO Auto-generated method stub
		return StringUtil.stringUtilFile(titleDataBase);

	}
	@Override
	public void addSubItens(List<CollectionSubItem> subItens) {
		// TODO Auto-generated method stub
		subItens.forEach(subItem -> listChapters.add((ChapterDownloaded) subItem));

	}
	@Override
	public void setListSubItems(List<CollectionSubItem> subItems) {
		List<ChapterDownloaded> items = new ArrayList<ChapterDownloaded>();
		subItems.forEach(subItem -> items.add((ChapterDownloaded) subItem));
		listChapters = items;
	}

	public List<CollectionSubItem> getListSubItens() {
		List<CollectionSubItem> subItens = new ArrayList<>();
		listChapters.forEach(ch -> subItens.add(ch));
		return subItens;
	}

	@Override
	public Collections getCollections() {
		// TODO Auto-generated method stub
		return mangaCollection;
	}

	@Override
	public void setCollections(Collections collections) {
		// TODO Auto-generated method stub
		this.mangaCollection = (MangaCollection) collections;
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
		return "MangaDownloaded [name=" + name + ", destination=" + destination + ", dataUrl=" + dataUrl
				+ ", titleDataBase=" + titleDataBase + ", titleFileName=" + titleFileName + ", imageUrl=" + imageUrl
				+ ", imageLocal=" + imageLocal + ", id=" + id + "]";
	}





	@Override
	public ItemForm toForm() {
		// TODO Auto-generated method stub
		return null;
	}



}
