package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class ItemsSelectedSingle<T> implements Controllers {

	private ComboBox<T> cbItems;
	private boolean visible;

	public ItemsSelectedSingle(ComboBox<T> cbItems) {

		this.cbItems = cbItems;
	}

	public ComboBox<T> getComboBoxItems() {
		return cbItems;
	}

	@Override
	public List<Node> getChildren() {

		return Arrays.asList(cbItems);
	}

	@Override
	public FetchItemType getFetchItemType() {

		return FetchItemType.SINGLE;
	}

	@Override
	public void setVisible(boolean visible) {
		cbItems.setVisible(visible);
		this.visible = visible;
	}

	@Override
	public boolean getVisible() {

		return visible;
	}

}
