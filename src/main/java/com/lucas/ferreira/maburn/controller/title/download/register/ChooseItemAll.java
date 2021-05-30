package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedAll;

public class ChooseItemAll implements ChooseItem{
	private ItemsSelectedAll itemsSelectedAll;
	private Map<String, String> namedItemsValues;
	private List<String> valuesItems;
	public ChooseItemAll(ItemsSelectedAll itemsSelectedAll,List<String> valuesItems, Map<String, String> namedItemsValues) {
		this.itemsSelectedAll  = itemsSelectedAll;
		this.valuesItems = valuesItems;
		this.namedItemsValues = namedItemsValues;
	
	}	
	
	@Override
	public List<String> getChoosedItems() {
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < valuesItems.size(); i++) {
			String item = valuesItems.get(i);
			items.add(namedItemsValues.get(item));
		}
		
		return items;
	}

	@Override
	public Controllers getController() {
		// TODO Auto-generated method stub
		return itemsSelectedAll;
	}
	
}
