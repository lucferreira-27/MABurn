package com.lucas.ferreira.maburn.model.documents.xml.form;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CollectionForm {
	@JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(localName = "items")
    private List<ItemForm> items = new ArrayList<>(); 
	
	public List<ItemForm> getItems() {
		return items;
	}
	
	public void setItem(List<ItemForm> items) {
		this.items = items;
	}
	
}
