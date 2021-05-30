package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FetchTypeSelect implements ChangeListener<FetchItemType> {


	private List<Controllers> controllers;
	private FetchItemType fetchItemType;


	public FetchTypeSelect(List<Controllers> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void changed(ObservableValue<? extends FetchItemType> observable, FetchItemType oldValue,
			FetchItemType newValue) {
		fetchItemType = newValue;
		showTargetController(fetchItemType);
		hideOthersControler(fetchItemType);

	}

	private void showTargetController(FetchItemType fetchItemType) {
		
		
		
		controllers
				.stream()
				.filter(controller -> controller.getFetchItemType() == fetchItemType)
				.forEach(controller -> controller.setVisible(true));
	}
	private void hideOthersControler(FetchItemType fetchItemType) {
		
		
		
		controllers
		.stream()
		.filter(controller -> controller.getFetchItemType() != fetchItemType)
		.forEach(controller -> controller.setVisible(false));
		

	}
	
	public FetchItemType getFetchItemType() {
		return fetchItemType;
	}



}
