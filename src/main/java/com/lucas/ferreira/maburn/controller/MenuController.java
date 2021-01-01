package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable {


	private MainInterfaceView mainView;
	private HomeInterfaceView homeView = new HomeInterfaceView();
	private HelperInterfaceView helperView = new HelperInterfaceView();
	private InitializeModel initialize;
	
	@FXML
	private Button btnConfig;
	
	@FXML
	private Button btnHome;
	
	@FXML
	private Button btnExtra;
	
	
	public MenuController(MainInterfaceView mainView) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;
		initialize = new InitializeModel(helperView);

	}

	public boolean active() {
		return true;

	}
	
	@FXML
	public void onClickButtonHome() {

		System.out.println("Home");
		new Thread(() -> {
			
			homeView.loadMainInterfaceFX(mainView);
			System.out.println("Ok!");

		}).start();

	}
	@FXML
	public void onClickButtonConfiguration() {
		System.out.println("Configuration");
		new Thread(() -> {
			helperView.loadMainInterfaceFX(mainView);

			System.out.println("Ok!");

		}).start();

	}
	@FXML
	public void onClickButtonExtra() {
		System.out.println("Extra");
		new Thread(() -> {
			System.out.println("Ok!");

		}).start();
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
		return btnExtra;
	}

}
