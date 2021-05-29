package com.lucas.ferreira.maburn.controller.title.download.validador;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface Validador {
	public BooleanProperty validate();
	public BooleanProperty getValidate();
	public StringProperty getError();
}
