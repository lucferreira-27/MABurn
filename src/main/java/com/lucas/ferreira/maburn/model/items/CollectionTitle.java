package com.lucas.ferreira.maburn.model.items;

import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;

import javafx.scene.image.Image;

public interface CollectionTitle {
	
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
	public void addSubItens(List<CollectionItem> subItem);
	public void setListSubItems(List<CollectionItem> subItem);
	public Collections getCollections();
	public void setCollections(Collections collections);
	public List<CollectionItem> getListSubItens();
//	public WebScraping getWebScraping();
//	public void setWebScraping(WebScraping webScraping);
	public Double getScore();
	public void setScore(Double score);
	public String getDate();
	public void setDate(String date);
	
	public ListItemForm toForm();


	

}
