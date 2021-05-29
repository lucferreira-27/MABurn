package com.lucas.ferreira.maburn.controller.title.download.controllers;


import com.lucas.ferreira.maburn.model.enums.FetchItemType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class FetchTypeSelect implements ChangeListener<FetchItemType> {
	
	private ComboBox<String> cbItems;
	private ItemsTextField<String> itemsTextField;
	public FetchTypeSelect(ComboBox<String> cbItems, ItemsTextField<String> itemsTextField ) {
		// TODO Auto-generated constructor stub
		this.cbItems = cbItems;
		this.itemsTextField = itemsTextField;
	}


	@Override
	public void changed(ObservableValue<? extends FetchItemType> observable, FetchItemType oldValue,
			FetchItemType newValue) {
			if(FetchItemType.BETWEEN == newValue) {
				hideComboBoxItems();
				showTextFields();
			}if(FetchItemType.SINGLE == newValue) {
				showComboBoxItems();
			}
		
	}
	
	private void showTextFields() {
		itemsTextField.setVisible(true);
	}
	private void hideTextFields() {
		itemsTextField.setVisible(false);
	}
	
	private void showComboBoxItems() {
		cbItems.setVisible(true);
	}
	private void hideComboBoxItems() {
		cbItems.setVisible(false);
	}
	public ComboBox<String> getCbItems() {
		return cbItems;
	}


}
