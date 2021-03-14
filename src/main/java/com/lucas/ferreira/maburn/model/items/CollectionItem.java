package com.lucas.ferreira.maburn.model.items;

import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

import javafx.scene.image.Image;

public interface CollectionItem {
	
	public String getName();
	public void setName(String name);
	public String getHospedSite();
	public void setHospedSite(String hospedSite);
	public String getDestination();
	public void setDestination(String destination);
	public String getLink();
	public void setLink(String link);
	public String getDataBaseUrl();
	public void setDataBaseUrl(String url);
	public String getImageUrl();
	public void setImageUrl(String imageUrl);
	public String getImageLocal();
	public void setImageLocal(String local);
	public Image getImage();
	public void setImage(Image image);
	public int getId();
	public void setId(int id);
	public Category getCategory();
	public String getTitleDataBase();
	public Map<String, String> getTitles();
	public void setTitles(Map<String, String> titles);
	public void setTitleDataBase(String titleDataBase);
	public String getTitleFileName();
	public void addSubItens(List<CollectionSubItem> subItem);
	public void setListSubItems(List<CollectionSubItem> subItem);
	public Collections getCollections();
	public void setCollections(Collections collections);
	public List<CollectionSubItem> getListSubItens();
	public WebScraping getWebScraping();
	public void setWebScraping(WebScraping webScraping);
	public ItemForm toForm();


	

}
