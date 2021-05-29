package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class ItemCombox {


	public ItemCombox() {
	}
	public Map<String, String> itemsValuesMap(TitleScraped titleScraped, List<String> namedItems) {
		Map<String, String> namedItemsValues = new HashMap<String, String>();
		List<String> items = titleScraped.getItemsScraped();

		namedItems.forEach(n -> namedItemsValues.put(n, items.get(namedItems.indexOf(n))));
		return namedItemsValues;
	}

	public List<String> valuesToNumberTagItems(TitleScraped titleScraped, String tag) {

		List<String> items = titleScraped.getItemsScraped();
		List<String> namedItems = items.stream().mapToInt(item -> items.indexOf(item) + 1).boxed()
				.map(n -> tag + n).collect(Collectors.toList());
		return namedItems;
	}


}
