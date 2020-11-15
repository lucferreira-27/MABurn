package com.lucas.ferreira.maburn.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class TableCollectionItemModel {
	private final SimpleBooleanProperty selected;
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty size;
	private final SimpleStringProperty path;
	private final Button btnFolder;

	public TableCollectionItemModel(String name, Button btnFolder, Integer size, String path) {
		this.btnFolder = btnFolder;
		this.selected = new SimpleBooleanProperty(false);
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleIntegerProperty(size);
		this.path = new SimpleStringProperty(path);
	}

	public boolean isSelected() {
		return selected.get();
	}

	public String getName() {
		return name.get();
	}

	public int getSize() {
		return size.get();
	}

	public String getPath() {
		return path.get();
	}

	public SimpleBooleanProperty selectedProprety() {
		return selected;
	}

	public SimpleStringProperty nameProprety() {
		return name;
	}

	public SimpleIntegerProperty sizeProprety() {
		return size;
	}

	public SimpleStringProperty pathProprety() {
		return path;
	}
	
	public Button getBtnFolder() {
		return btnFolder;
	}

}
