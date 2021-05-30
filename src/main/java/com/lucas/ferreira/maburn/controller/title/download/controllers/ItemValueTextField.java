package com.lucas.ferreira.maburn.controller.title.download.controllers;

import com.lucas.ferreira.maburn.controller.title.download.validador.ItemValueNumberValidador;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ItemValueTextField {

	private final TextField txtField;
	private int totalItems;
	private final TextArea txtArea;
	private ItemValueNumberValidador itemValueNumberValidador;
	private boolean geralError = false;
	private boolean visible;
	
	public ItemValueTextField(TextField txtField, int totalItems, TextArea txtArea) {
		if(txtField == null) {
			throw new NullPointerException("TextField can't be null");
		}
		if(txtArea == null) {
			throw new NullPointerException("TextArea can't be null");
		}
		this.txtField = txtField;
		this.totalItems = totalItems;
		this.txtArea = txtArea;
	}

	public void validate() {
		itemValueNumberValidador = new ItemValueNumberValidador(txtField, totalItems);

		Message errorMessage = new ErrorMessage(txtArea);

		itemValueNumberValidador.getError().addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue.equals("OK")) {
					bottomLineErrorStyle();
					// this.comparatorErrorFilled = true;

					errorMessage.showMessage(newvalue);
				
			} else {
				if (!geralError) {
					System.out.println("normal: " + geralError);
					bottomLineNormalStyle();
					// this.comparatorErrorFilled = false;
				}
				txtArea.clear();

			}
		});

		itemValueNumberValidador.validate();

	}

	public void bottomLineErrorStyle() {
		txtField.setStyle("-fx-border-color: #A32020;");
	}

	public void bottomLineNormalStyle() {
		txtField.setStyle("-fx-border-color: white;");
	}

	public ItemValueNumberValidador getItemValueNumberValidador() {
		return itemValueNumberValidador;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public TextArea getTxtArea() {
		return txtArea;
	}
	
	public void setVisible(boolean visible) {
		txtArea.setVisible(visible);
		txtField.setVisible(visible);
		
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public TextField getTxtField() {
		return txtField;
	}

	public boolean isGeralError() {
		return geralError;
	}
	public void setGeralError(boolean geralError) {
		this.geralError = geralError;
	}


}
