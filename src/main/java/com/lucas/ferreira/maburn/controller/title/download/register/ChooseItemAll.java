package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedAll;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedUpdate;
import com.lucas.ferreira.maburn.util.MapKeyValue;

public class ChooseItemAll implements ChooseItem{
	private ItemsSelectedAll itemsSelectedAll;
	private Map<String, String> namedItemsValues;
	public ChooseItemAll(ItemsSelectedAll itemsSelectedAll,Map<String, String> namedItemsValues) {
		this.itemsSelectedAll  = itemsSelectedAll;
		this.namedItemsValues = namedItemsValues;
	
	}	
	
	@Override
	public Map<String, String> getChoosedItems() {
		List<String> values = new ArrayList<String>(namedItemsValues.values());
		Map<String, String> choosedItemsMap = new LinkedHashMap<String, String>();

		for (int i = 0; i < values.size(); i++) {
			String value = values.get(i);
			String key = MapKeyValue.getKeyByValue(namedItemsValues, value);
			choosedItemsMap.put(key, value);
		}
		
		return choosedItemsMap;
	}

	@Override
	public Controllers getController() {
		return itemsSelectedAll;
	}

}
