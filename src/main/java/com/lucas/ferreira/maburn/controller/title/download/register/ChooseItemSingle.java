package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedSingle;

import javafx.scene.control.ComboBox;

public class ChooseItemSingle<T>  implements ChooseItem{

	private ItemsSelectedSingle<T> itemsSelectedSingle;
	private Map<String, String> namedItemsValues;
	public ChooseItemSingle(ItemsSelectedSingle<T> itemsSelectedSingle, Map<String, String> namedItemsValues) {
		this.itemsSelectedSingle = itemsSelectedSingle;
		this.namedItemsValues = namedItemsValues;
	}
	
	@Override
	public List<String> getChoosedItems() {
		
		ComboBox<T> cbItems = itemsSelectedSingle.getComboBoxItems();
		return Arrays.asList(namedItemsValues.get(cbItems.getValue()));
		
	}

	@Override
	public Controllers getController() {
		// TODO Auto-generated method stub
		return itemsSelectedSingle;
	}

}
