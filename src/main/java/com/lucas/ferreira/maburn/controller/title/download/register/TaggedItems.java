package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class TaggedItems {

	private List<String> valuesItems;
	private List<String> keyItems;
	private Map<String, String> namedItemsValues;
	private String tag;

	public TaggedItems(List<String> valuesItems, String tag) {
		this.valuesItems = valuesItems;
		this.tag = tag;
	}
	
	
	public void tagItems() {
		linkValueToTag();
		linkValueToNamedItem();
	}
	
	private void linkValueToNamedItem() {
		namedItemsValues = new LinkedHashMap<String, String>();

		keyItems.forEach(n -> namedItemsValues.put(n, valuesItems.get(keyItems.indexOf(n))));
	}

	
	//VALUES NEED BE SHORT
	private void linkValueToTag() {

		keyItems = valuesItems.stream().mapToInt(item -> valuesItems.indexOf(item) + 1).boxed().map(n -> tag + n)
				.collect(Collectors.toList());
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

	public String getTag() {
		return tag;
	}

}
