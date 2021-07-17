package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.validador.ItemValueCompareValidator;
import com.lucas.ferreira.maburn.controller.title.download.validador.ItemValueNumberValidador;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ItemsSelectedBetween implements Controllers {

	private final TextArea txtAreaMessage;

	private ItemValueCompareValidator itemValueCompareValidator;
	private ItemValueTextField itemValueTextFieldBegin;
	private ItemValueTextField itemValueTextFieldEnd;
	private BooleanProperty validate = new SimpleBooleanProperty();
	private boolean visible;
	
	
	public ItemsSelectedBetween(ItemValueTextField itemValueTextFieldBegin, ItemValueTextField itemValueTextFieldEnd,
			TextArea txtAreaMessage) {
	 
		
		this.itemValueTextFieldBegin = itemValueTextFieldBegin;
		this.itemValueTextFieldEnd = itemValueTextFieldEnd;
		this.txtAreaMessage = txtAreaMessage;
	}

	public void validates() {

		itemValueTextFieldBegin.validate();
		itemValueTextFieldEnd.validate();
		ItemValueNumberValidador numberValidadorBegin = itemValueTextFieldBegin.getItemValueNumberValidador();
		ItemValueNumberValidador numberValidadorEnd = itemValueTextFieldEnd.getItemValueNumberValidador();

		itemValueCompareValidator = new ItemValueCompareValidator(numberValidadorBegin, numberValidadorEnd);
		validate.bindBidirectional(itemValueCompareValidator.validate());

		Message errorMessage = new ErrorMessage(txtAreaMessage);

		itemValueCompareValidator.getError().addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue.equals("OK")) {
				itemValueTextFieldBegin.bottomLineErrorStyle();
				itemValueTextFieldEnd.bottomLineErrorStyle();

				itemValueTextFieldBegin.setGeralError(true);
				itemValueTextFieldEnd.setGeralError(true);

				errorMessage.showMessage(newvalue);
			} else {
				itemValueTextFieldBegin.bottomLineNormalStyle();
				itemValueTextFieldEnd.bottomLineNormalStyle();

				itemValueTextFieldBegin.setGeralError(false);
				itemValueTextFieldEnd.setGeralError(false);
				txtAreaMessage.clear();

			}
		});
	}
	
	
	public TextArea getTextAreaMessage() {
		return txtAreaMessage;
	}
	
	public ItemValueCompareValidator getItemValueCompareValidator() {
		return itemValueCompareValidator;
	}

	public ItemValueTextField getItemValueTextFieldBegin() {
		return itemValueTextFieldBegin;
	}

	public ItemValueTextField getItemValueTextFieldEnd() {
		return itemValueTextFieldEnd;
	}


	public boolean isVisible() {
		return visible;
	}
	public BooleanProperty getValidate() {
		return validate;
	}

	@Override
	public List<Node> getChildren() {
		
		return Arrays.asList(itemValueTextFieldBegin.getTxtField(), itemValueTextFieldEnd.getTxtField());
	}

	@Override
	public FetchItemType getFetchItemType() {
		
		return FetchItemType.BETWEEN;
	}
	
	
	@Override
	public void setVisible(boolean visible) {
		itemValueTextFieldBegin.getTxtField().setVisible(visible);
		itemValueTextFieldEnd.getTxtField().setVisible(visible);
		this.visible = visible;
	}

	@Override
	public boolean getVisible() {
		
		return visible;
	}

}
