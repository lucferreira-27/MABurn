package com.lucas.ferreira.maburn.model.itens;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;

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
	public Image getCover();
	public void setCover(Image cover);
	public int getId();
	public void setId(int id);
	public Category getCategory();
	public String getTitleDataBase();
	public void setTitleFromDataBase(String titleDataBase);
	public void setListSubItens(List<CollectionSubItem> subItem);
	public List<CollectionSubItem> getListSubItens();
}
