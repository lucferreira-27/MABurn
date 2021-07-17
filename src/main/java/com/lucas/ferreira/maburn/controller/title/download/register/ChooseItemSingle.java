package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedSingle;

import javafx.scene.control.ComboBox;

public class ChooseItemSingle implements ChooseItem{

	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private Map<String, String> namedItemsValues;
	public ChooseItemSingle(ItemsSelectedSingle<String> itemsSelectedSingle, Map<String, String> namedItemsValues) {
		this.itemsSelectedSingle = itemsSelectedSingle;
		this.namedItemsValues = namedItemsValues;
	}
	
	@Override
	public Map<String, String> getChoosedItems() {
		ComboBox<String> cbItems = itemsSelectedSingle.getComboBoxItems();
		String key = cbItems.getValue();
		String value = namedItemsValues.get(key);
		Map<String, String> choosedItemsMap = ImmutableMap.of(key, value);
		return choosedItemsMap;
		
	}

	@Override
	public Controllers getController() {
		
		return itemsSelectedSingle;
	}

}
