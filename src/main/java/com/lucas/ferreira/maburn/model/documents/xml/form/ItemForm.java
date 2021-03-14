package com.lucas.ferreira.maburn.model.documents.xml.form;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class ItemForm {

	private Category category;
	private Integer id;
	private String titleEn;
	private String tittleEnJp;
	private String titleJaJp;
	private String imageUrl;
	private String imageLocal;
	private String destination;
	private String dataUrl;
	private String titleDatabase;
	private List<SiteForm> sitesSearched = new ArrayList<SiteForm>();
	private List<String> scrapingLinks = new ArrayList<String>();
	private List<String> updateScrapingLinks = new ArrayList<String>();
	private String curretScrapingLink;
	
	
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	public String getTittleEnJp() {
		return tittleEnJp;
	}
	public void setTittleEnJp(String tittleEnJp) {
		this.tittleEnJp = tittleEnJp;
	}
	public String getTitleJaJp() {
		return titleJaJp;
	}
	public void setTitleJaJp(String titleJaJp) {
		this.titleJaJp = titleJaJp;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageLocal() {
		return imageLocal;
	}
	public void setImageLocal(String imageLocal) {
		this.imageLocal = imageLocal;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDataUrl() {
		return dataUrl;
	}
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	public String getTitleDatabase() {
		return titleDatabase;
	}
	public void setTitleDatabase(String titleDatabase) {
		this.titleDatabase = titleDatabase;
	}
	public List<SiteForm> getSitesSearched() {
		return sitesSearched;
	}
	public void setSitesSearched(List<SiteForm> sites) {
		this.sitesSearched = sites;
	}
	public List<String> getScrapingLinks() {
		return scrapingLinks;
	}
	public void setScrapingLinks(List<String> scrapingLinks) {
		this.scrapingLinks = scrapingLinks;
	}
	public List<String> getUpdateScrapingLinks() {
		return updateScrapingLinks;
	}
	public void setUpdateScrapingLinks(List<String> updateScrapingLinks) {
		this.updateScrapingLinks = updateScrapingLinks;
	}
	public String getCurretScrapingLink() {
		return curretScrapingLink;
	}
	public void setCurretScrapingLink(String curretScrapingLink) {
		this.curretScrapingLink = curretScrapingLink;
	}
	
	public CollectionItem toCollectionItem() {
		CollectionItem collectionItem = null;
		if(category == Category.ANIME) {
			collectionItem = new AnimeDownloaded();
		}else if(category == Category.MANGA) {
			collectionItem = new MangaDownloaded();
		}
		collectionItem.setDestination(destination);
		collectionItem.setDestination(destination);
		collectionItem.setTitleDataBase(titleDatabase);
		collectionItem.setDataBaseUrl(dataUrl);
		collectionItem.setImageLocal(imageLocal);
		collectionItem.setImageUrl(imageUrl);
		collectionItem.setId(id);
		return collectionItem;
	}
	
	

	
	
	
	
}
