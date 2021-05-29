package com.lucas.ferreira.maburn.controller.title.download.validador;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

public class ItemValueNumberValidador implements Validador {

	private final static String ONLY_NUMBERS_REGEX = "^[0-9]*$";
	private StringProperty error = new SimpleStringProperty();
	private BooleanProperty validate = new SimpleBooleanProperty();
	private TextField txtField;
	private int totalItems;

	public ItemValueNumberValidador(TextField txtField, int totalItems) {
		this.txtField = txtField;
		this.totalItems = totalItems;
	}

	@Override
	public BooleanProperty validate() {
		
		txtField.textProperty().addListener((obs, oldvalue, newvalue) ->{
			if(txtField.getText().length() == 1 && newvalue.equals("0")) {
				Platform.runLater(() -> {
					txtField.setText(txtField.getText().replace("0", "1"));
					txtField.end();
				});
			}
		});
		
		txtField.textProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == null || newvalue.isEmpty()) {
				error.set("Field can't be empty.");
				validate.set(false);
			}else if (newvalue.matches(ONLY_NUMBERS_REGEX)) {
				
				if(totalItems < Integer.valueOf(newvalue)) {
					System.out.println("Total Items: " + totalItems  + " [ " + txtField + " ]");
					validate.set(false);
					error.set("Value can't be big than total of items (" + totalItems +")");
				}else {
					
					validate.set(true);
					error.set("OK");
				}

			} else {
				validate.set(false);
				error.set("Invalid character.");
			}
		});
		return validate;
	}

	@Override
	public BooleanProperty getValidate() {
		return validate;
	}

	public TextField getTxtField() {
		return txtField;
	}

	@Override
	public StringProperty getError() {
		return error;
	}
}
