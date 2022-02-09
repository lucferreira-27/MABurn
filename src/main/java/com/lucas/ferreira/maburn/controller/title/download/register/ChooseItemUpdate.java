package com.lucas.ferreira.maburn.controller.title.download.register;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedUpdate;
import com.lucas.ferreira.maburn.util.MapKeyValue;

public class ChooseItemUpdate implements ChooseItem{
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private Map<String, String> namedItemsValues;
	public ChooseItemUpdate(ItemsSelectedUpdate itemsSelectedUpdate,Map<String, String> namedItemsValues) {
		this.itemsSelectedUpdate  = itemsSelectedUpdate;
		this.namedItemsValues = namedItemsValues;
	}
	
	@Override
	public Map<String, String> getChoosedItems() {
		List<String> keys = itemsSelectedUpdate.getItemsKeys();
		Map<String, String> choosedItemsMap = new LinkedHashMap<>();

		for(String key : keys){
			String value = namedItemsValues.get(key);
			choosedItemsMap.put(key,value);
		}

		return choosedItemsMap;
	}

	@Override
	public Controllers getController() {
		
		return itemsSelectedUpdate;
	}

}
