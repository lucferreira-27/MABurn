package com.lucas.ferreira.maburn.model.collections;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public interface Collections {
	public List<? extends CollectionItem> getItens();

	public void setItens(List<? extends CollectionItem> itens);

	public void setDestination(String destination);

	public String getDestination();

	public void setActualItem(CollectionItem obj);

	public CollectionItem getActualItem();

	public Category getCategory();
}
