package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedBetween;

import javafx.scene.control.TextField;

public class ChooseItemBetween implements ChooseItem{
	private ItemsSelectedBetween itemsSelectedBetween;
	private List<String> valuesItems;
	public ChooseItemBetween(ItemsSelectedBetween itemsSelectedBetween, List<String> valuesItems) {
		this.itemsSelectedBetween = itemsSelectedBetween;
		this.valuesItems = valuesItems;
		
	}
	@Override
	public List<String> getChoosedItems() {
		
		TextField txtBegin = itemsSelectedBetween.getItemValueTextFieldBegin().getTxtField();
		TextField txtEnd = itemsSelectedBetween.getItemValueTextFieldEnd().getTxtField();

		int beginValue = Integer.valueOf(txtBegin.getText()) - 1;
		int endValue = Integer.valueOf(txtEnd.getText()) - 1;

		List<String> itemsBetween = new ArrayList<String>();

		for (int i = beginValue; i <= endValue; i++) {
			String item = valuesItems.get(i);
			itemsBetween.add(item);
		}
		return itemsBetween;
			}
	@Override
	public Controllers getController() {
		// TODO Auto-generated method stub
		return itemsSelectedBetween;
	}

}
