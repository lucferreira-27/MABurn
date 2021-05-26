package com.lucas.ferreira.maburn.model.collections;

import java.util.List;

import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public interface Collections {
	public List<CollectionTitle> getItens();

	public void setItens(List<CollectionTitle> itens);

	public void setDestination(String destination);

	public String getDestination();

	public void setActualItem(CollectionTitle obj);

	public CollectionTitle getActualItem();

	public Category getCategory();
	
}
