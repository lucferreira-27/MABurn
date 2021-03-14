package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.DownloadQueueInterfaceView;
import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable {


	private HomeInterfaceView homeView = new HomeInterfaceView();
	private HelperInterfaceView helperView = new HelperInterfaceView();
	private DownloadQueueInterfaceView queueView=  new DownloadQueueInterfaceView();
	private InitializeModel initialize;
	
	@FXML
	private Button btnConfig;
	
	@FXML
	private Button btnHome;
	
	@FXML
	private Button btnExtra;
	
	
	public MenuController() {
		// TODO Auto-generated constructor stub
		initialize = new InitializeModel();

	}

	public boolean active() {
		return true;

	}
	
	@FXML
	public void onClickButtonHome() {

		CustomLogger.log("Home");
		new Thread(() -> {
			
			homeView.loadMainInterfaceFX();
			CustomLogger.log("Ok!");

		}).start();

	}
	@FXML
	public void onClickButtonConfiguration() {
		CustomLogger.log("Configuration");
		new Thread(() -> {
			helperView.loadMainInterfaceFX();

			CustomLogger.log("Ok!");

		}).start();

	}
	@FXML
	public void onClickButtonDownloads() {
		CustomLogger.log("Downloads");
		new Thread(() -> {
			queueView.loadMainInterfaceFX();

			CustomLogger.log("Ok!");

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
