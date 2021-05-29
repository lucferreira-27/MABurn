package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Sites;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class RegisterFetchChoose {

	private final ComboBox<FetchItemType> cbSelect;
	private final ComboBox<String> cbItems;
	private final TextArea txtErrorArea;
	private final Button btnDownload;
	
	public RegisterFetchChoose(ComboBox<FetchItemType> cbSelect, ComboBox<String> cbItems, Button btnDownload ,TextArea txtErrorArea) {
		this.cbSelect = cbSelect;
		this.cbItems = cbItems;
		this.btnDownload = btnDownload;
		this.txtErrorArea = txtErrorArea;
		
	}




	public ComboBox<FetchItemType> getCbSelect() {
		return cbSelect;
	}


	public ComboBox<String> getCbItems() {
		return cbItems;
	}


	public TextArea getTxtErrorArea() {
		return txtErrorArea;
	}
	public Button getBtnDownload() {
		return btnDownload;
	}
	
	
}
