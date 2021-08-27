package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedAll;

public class ChooseItemAll implements ChooseItem{
	private ItemsSelectedAll itemsSelectedAll;
	private Map<String, String> namedItemsValues;
	public ChooseItemAll(ItemsSelectedAll itemsSelectedAll,Map<String, String> namedItemsValues) {
		this.itemsSelectedAll  = itemsSelectedAll;
		this.namedItemsValues = namedItemsValues;
	
	}	
	
	@Override
	public Map<String, String> getChoosedItems() {
		Map<String, String> choosedItemsMap = namedItemsValues;

		
		return choosedItemsMap;
	}

	@Override
	public Controllers getController() {
		return itemsSelectedAll;
	}

}
