package com.lucas.ferreira.maburn.controller.title.download.controllers;

import com.lucas.ferreira.maburn.controller.title.download.validador.ItemValueCompareValidator;
import com.lucas.ferreira.maburn.controller.title.download.validador.ItemValueNumberValidador;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ItemsTextField<T> {

	
	private final TextArea txtAreaMessage;
	
	private ItemValueCompareValidator itemValueCompareValidator;
	private  ItemValueTextField itemValueTextFieldBegin;
	private  ItemValueTextField itemValueTextFieldEnd;


	public ItemsTextField(ItemValueTextField itemValueTextFieldBegin, ItemValueTextField itemValueTextFieldEnd, TextArea txtAreaMessage) {
		this.itemValueTextFieldBegin = itemValueTextFieldBegin;
		this.itemValueTextFieldEnd = itemValueTextFieldEnd;
		this.txtAreaMessage = txtAreaMessage;

	}

	public void validates() {
		
		
		itemValueTextFieldBegin.validate();
		itemValueTextFieldEnd.validate();
		ItemValueNumberValidador numberValidadorBegin = itemValueTextFieldBegin.getItemValueNumberValidador();
		ItemValueNumberValidador numberValidadorEnd = itemValueTextFieldEnd.getItemValueNumberValidador();

		
		itemValueCompareValidator = new ItemValueCompareValidator(numberValidadorBegin,
				numberValidadorEnd);
		itemValueCompareValidator.validate();
		
		Message errorMessage = new ErrorMessage(txtAreaMessage);

		itemValueCompareValidator.getError().addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue.equals("OK")) {
				itemValueTextFieldBegin.bottomLineErrorStyle();
				itemValueTextFieldEnd.bottomLineErrorStyle();

				itemValueTextFieldBegin.setGeralError(true);
				itemValueTextFieldEnd.setGeralError(true);

				errorMessage.showMessage(newvalue);
			}
			else {
				itemValueTextFieldBegin.bottomLineNormalStyle();
				itemValueTextFieldEnd.bottomLineNormalStyle();
				
				itemValueTextFieldBegin.setGeralError(false);
				itemValueTextFieldEnd.setGeralError(false);
				txtAreaMessage.clear();

			}
		});
	}



	public ItemValueCompareValidator getItemValueCompareValidator() {
		return itemValueCompareValidator;
	}


	public void setVisible(boolean visible) {
		itemValueTextFieldBegin.getTxtField().setVisible(visible);
		itemValueTextFieldEnd.getTxtField().setVisible(visible);
	}

}
