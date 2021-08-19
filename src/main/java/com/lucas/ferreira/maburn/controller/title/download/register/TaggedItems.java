package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaggedItems {

	private List<String> valuesItems;
	private List<String> keyItems;
	private Map<String, String> namedItemsValues;


	
	
	public TaggedItems(Map<String, String> itemsScraped) {
		this.namedItemsValues = itemsScraped;
		keyItems = new ArrayList<>(itemsScraped.keySet());
		valuesItems = new ArrayList<String>(itemsScraped.values());
	}
	
	public List<String> getKeyItems() {
		return keyItems;
	}

	public Map<String, String> getNamedItemsValues() {
		return namedItemsValues;
	}

	public List<String> getValuesItems() {
		return valuesItems;
	}


}
