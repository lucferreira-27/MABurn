package com.lucas.ferreira.maburn.controller.title.download.installer;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.ModelInitialiable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class BrowserInstallerModel implements ModelInitialiable{

	@FXML
	private BorderPane bpConteiner;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblProgres;
	@FXML
	private ImageView imgBrowserInstall;
	@FXML
	private ImageView imgClose;
	@FXML
	private Button btnAction;
	@FXML
	private ProgressBar pbPogress;
	
	


	public BorderPane getBpConteiner() {
		return bpConteiner;
	}




	public Label getLblStatus() {
		return lblStatus;
	}




	public Label getLblTitle() {
		return lblTitle;
	}




	public Label getLblProgres() {
		return lblProgres;
	}




	public ImageView getImgBrowserInstall() {
		return imgBrowserInstall;
	}




	public ImageView getImgClose() {
		return imgClose;
	}




	public Button getBtnAction() {
		return btnAction;
	}




	public ProgressBar getPbPogress() {
		return pbPogress;
	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
