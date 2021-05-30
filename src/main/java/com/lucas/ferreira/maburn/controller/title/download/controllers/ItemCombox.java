package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class ItemCombox {


	public ItemCombox() {
	}
	public Map<String, String> itemsValuesMap(List<String> valuesItems, List<String> namedItems) {
		Map<String, String> namedItemsValues = new HashMap<String, String>();


		namedItems.forEach(n -> namedItemsValues.put(n, valuesItems.get(namedItems.indexOf(n))));
		return namedItemsValues;
	}

	public List<String> valuesToNumberTagItems(List<String> valuesItems, String tag) {

		List<String> namedItems = valuesItems.stream().mapToInt(item -> valuesItems.indexOf(item) + 1).boxed()
				.map(n -> tag + n).collect(Collectors.toList());
		return namedItems;
	}


}
