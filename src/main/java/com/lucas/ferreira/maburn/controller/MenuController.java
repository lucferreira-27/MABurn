package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MenuController implements Initializable {

	private Navigator navigator = new Navigator();
	private InitializeModel initialize;

	@FXML
	private Button btnConfig;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnDownloads;

	public MenuController() {
		// TODO Auto-generated constructor stub
		initialize = new InitializeModel();

	}

	public boolean active() {
		return true;

	}

	@FXML
	public void onClickButtonHome() {

		navigator.open(Interfaces.HOME);


	}

	@FXML
	public void onClickButtonConfiguration() {
		navigator.open(Interfaces.CONFIGURATION);

	}

	@FXML
	public void onClickButtonDownloads() {

		navigator.open(Interfaces.DOWNLOADS);


	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initialize.boot();

	}

	public Button getBtnConfig() {
		return btnConfig;
	}

	public Button getBtnHome() {
		return btnHome;
	}

	public Button getBtnExtra() {
		return btnDownloads;
	}

}
