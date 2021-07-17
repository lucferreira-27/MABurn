package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedBetween;
import com.lucas.ferreira.maburn.util.MapKeyValue;

import javafx.scene.control.TextField;

public class ChooseItemBetween implements ChooseItem{
	private ItemsSelectedBetween itemsSelectedBetween;
	private Map<String, String> namedItemsValues;
	public ChooseItemBetween(ItemsSelectedBetween itemsSelectedBetween,  Map<String, String> namedItemsValues) {
		this.itemsSelectedBetween = itemsSelectedBetween;
		this.namedItemsValues = namedItemsValues;
		
	}
	@Override
	public Map<String, String> getChoosedItems() {
		
		TextField txtBegin = itemsSelectedBetween.getItemValueTextFieldBegin().getTxtField();
		TextField txtEnd = itemsSelectedBetween.getItemValueTextFieldEnd().getTxtField();

		int beginValue = Integer.valueOf(txtBegin.getText()) - 1;
		int endValue = Integer.valueOf(txtEnd.getText()) - 1;
		Map<String, String> choosedItemsMap = new LinkedHashMap<String, String>();
		List<String> values = new ArrayList<String>(namedItemsValues.values());

		for (int i = beginValue; i <= endValue; i++) {
			String value = values.get(i);
			String key = MapKeyValue.getKeyByValue(namedItemsValues, value);
			choosedItemsMap.put(key, value);
		}
		return choosedItemsMap;
			}	
	@Override
	public Controllers getController() {
		
		return itemsSelectedBetween;
	}
	
	

}
