package com.lucas.ferreira.maburn.controller.title.download.register;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.controllers.FetchTypeSelect;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;

public class FetchItemsLinks {
	
	private List<ChooseItem> chooseItems;

	public FetchItemsLinks(ChooseItem... arrayChooseItems) {
		 this.chooseItems = Arrays.asList(arrayChooseItems);
	}
	
	public List<String> selectedLinks(FetchTypeSelect fetchTypeSelect) {
		FetchItemType fetchItemType = fetchTypeSelect.getFetchItemType();

		ChooseItem chooseItem = chooseItems.stream()
				.filter(choose -> choose.getController().getFetchItemType() == fetchItemType).findFirst().get();

		List<String> choosedItems = chooseItem.getChoosedItems();
		
		return choosedItems;
	}
	
	
	
}	
