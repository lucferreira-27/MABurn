package com.lucas.ferreira.maburn.controller;

import javafx.beans.value.ChangeListener;

public interface ComboxValuesListening<T> {
	
	public void listen(ChangeListener<T> value);
}
