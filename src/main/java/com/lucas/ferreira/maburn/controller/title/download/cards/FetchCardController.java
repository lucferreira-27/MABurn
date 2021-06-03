package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FetchCardController implements Initializable {
	
	@FXML
	private Label lblItemName;
	@FXML
	private Label lblFetchState;
	@FXML
	private Label lblItemUrl;
	
	@FXML
	private ImageView imgFetchIcon;
	@FXML
	private ImageView imgLinkIcon;
	
	private FetchCardValues fetchCardValues;
	private String ICON_PATH = "icons/";

	
	public FetchCardController(FetchCardValues fetchCardValues) {
		this.fetchCardValues = fetchCardValues;
	}
	
	public void initializeValuesCard() {
		fetchCardValues.getItemUrl();
		lblFetchState.setText(fetchCardValues.getFetchCardState().getName());
		fetchCardValues.getFetchCardState().addListener((obs, oldvalue, newvalue) ->{
			lblFetchState.setText(newvalue.getName());
		});
		lblItemName.setText(fetchCardValues.getItemName());
		lblItemUrl.setText(fetchCardValues.getItemUrl());

		

	
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeValuesCard();
		initializeIcons();
	}
	public void initializeIcons() {
		Icon iconFetch = new Icon(imgFetchIcon, new IconConfig(ICON_PATH, Icons.FETCH_IN_CARD));
		iconFetch.setProperties();

		Icon iconLink = new Icon(imgLinkIcon, new IconConfig(ICON_PATH, Icons.LINK));
		iconLink.setProperties(event ->{
			System.out.println(fetchCardValues.getItemUrl());
		});
	}

}
