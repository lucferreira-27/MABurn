package com.lucas.ferreira.maburn.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ComboBoxCustom<T> {

	private final ComboBox<T> comboBox;

	public ComboBoxCustom(ComboBox<T> comboBox) {
		this.comboBox = comboBox;
	}

	public ListCell<T> addCellFactory(AnchorPane anchorPane) {
		IntegerProperty count = new SimpleIntegerProperty();
		return new ListCell<T>() {

			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				count.set(count.get() + 1);
				if (item == null || empty) {
					setGraphic(null);
				} else {
					anchorPane.setStyle("-fx-background: black;");
					setGraphic(anchorPane);
				}
			}
		};

	}

}
