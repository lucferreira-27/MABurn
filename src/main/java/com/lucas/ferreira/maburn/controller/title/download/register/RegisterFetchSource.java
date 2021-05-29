package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.util.Icon;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class RegisterFetchSource {

	private final ComboBox<Sites> cbSource;
	private final ImageView imgFetch;
	private final ImageView imgRecover;
	private final ImageView imgManualSearch;
	private final TextArea txtErrorArea;
	
	
	public RegisterFetchSource(ComboBox<Sites> cbSource, ImageView imgFetch, ImageView imgRecover, ImageView imgManualSearch,
			TextArea txtErrorArea) {
		this.cbSource = cbSource;
		this.imgFetch = imgFetch;
		this.imgRecover = imgRecover;
		this.imgManualSearch = imgManualSearch;
		this.txtErrorArea = txtErrorArea;
	}


	public ComboBox<Sites> getCbSource() {
		return cbSource;
	}




	public ImageView getImgFetch() {
		return imgFetch;
	}


	public ImageView getImgRecover() {
		return imgRecover;
	}


	public ImageView getImgManualSearch() {
		return imgManualSearch;
	}


	public TextArea getTxtErrorArea() {
		return txtErrorArea;
	}
	
	
	
	
	
	
	
}
