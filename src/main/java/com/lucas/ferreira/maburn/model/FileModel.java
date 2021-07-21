package com.lucas.ferreira.maburn.model;

import java.io.File;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileModel {
	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty size = new SimpleDoubleProperty();
	public StringProperty getName() {
		return name;
	}
	public DoubleProperty getSize() {
		return size;
	}

}
