package com.lucas.ferreira.maburn.controller.title.download.validador;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemValueCompareValidator implements Validador {
	private final static Integer TYPE_TIMELAPSE = 500;
	private BooleanProperty validate = new SimpleBooleanProperty();
	private StringProperty error = new SimpleStringProperty();
	private ItemValueNumberValidador itemValueNumberValidadorBegin;
	private ItemValueNumberValidador itemValueNumberValidadorEnd;

	public ItemValueCompareValidator(ItemValueNumberValidador itemValueNumberValidadorBegin,
			ItemValueNumberValidador itemValueNumberValidadorEnd) {
		this.itemValueNumberValidadorBegin = itemValueNumberValidadorBegin;
		this.itemValueNumberValidadorEnd = itemValueNumberValidadorEnd;
	}

	@Override
	public BooleanProperty validate() {

		validateOnTextType();
		validateOnFocus();
		
		return validate;
	}

	private void validateOnFocus() {
		itemValueNumberValidadorBegin.getTxtField().focusedProperty().addListener((obs, oldvalue, newvalue) -> {
			check();
		});
		itemValueNumberValidadorEnd.getTxtField().focusedProperty().addListener((obs, oldvalue, newvalue) -> {
			check();
		});
	}

	private void validateOnTextType() {
		itemValueNumberValidadorBegin.getTxtField().textProperty().addListener((obs, oldvalue, newvalue) -> {
			check();
		});
		itemValueNumberValidadorEnd.getTxtField().textProperty().addListener((obs, oldvalue, newvalue) -> {
			check();
		});
	}

	private void check() {

		if (itemValueNumberValidadorBegin.getValidate().get() && itemValueNumberValidadorEnd.getValidate().get()) {
			new Thread(() ->{
				long start = System.currentTimeMillis();
				while(TYPE_TIMELAPSE > System.currentTimeMillis() - start) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						 
						e.printStackTrace();
					}
				}
				try {
					Integer value1 = Integer.valueOf(itemValueNumberValidadorBegin.getTxtField().getText());
					Integer value2 = Integer.valueOf(itemValueNumberValidadorEnd.getTxtField().getText());

					if (value1.equals(value2)) {
						validate.set(false);
						error.set("Values can't be equals!");
					} else if (value1 > value2) {
						validate.set(false);
						error.set("Begin can't be big than End!");
					} else {
						validate.set(true);
						error.set("OK");
					}

				} catch (IllegalArgumentException e) {
					
					throw new RuntimeException(e);
				}
				
				
			}).start();
		}else {
			validate.set(false);
		}

	}

	@Override
	public BooleanProperty getValidate() {

		return validate;
	}

	@Override
	public StringProperty getError() {

		return error;
	}

}
