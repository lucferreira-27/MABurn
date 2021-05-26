package com.lucas.ferreira.maburn.controller;


import com.lucas.ferreira.maburn.model.enums.FetchItemType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class FetchTypeSelect implements ChangeListener<FetchItemType> {
	
	private ComboBox<String> cbItems;
	
	public FetchTypeSelect(ComboBox<String> cbItems) {
		// TODO Auto-generated constructor stub
		this.cbItems = cbItems;
	}


	@Override
	public void changed(ObservableValue<? extends FetchItemType> observable, FetchItemType oldValue,
			FetchItemType newValue) {
			if(FetchItemType.BETWEEN == newValue) {
				disableComboBoxItems();
			}else if(cbItems.isDisable()) {
				enableComboBoxItems();
			}
		
	}
	private void disableComboBoxItems() {
		cbItems.setDisable(true);
	}
	private void enableComboBoxItems() {
		cbItems.setDisable(false);
	}
	public ComboBox<String> getCbItems() {
		return cbItems;
	}


}
