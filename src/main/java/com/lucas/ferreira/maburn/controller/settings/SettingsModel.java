package com.lucas.ferreira.maburn.controller.settings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SettingsModel implements Initializable {

	@FXML
	private StackPane spMainPane;
	@FXML
	private VBox vBoxConfiguration;
	@FXML
	private Button btnAnimeCollection;
	@FXML
	private TextField txtPathAnimeCollection;
	@FXML
	private Button btnMangaCollection;
	@FXML
	private TextField txtPathMangaCollection;
	@FXML
	private Button btnInstall;

	@FXML
	private Label lblInstalled;
	@FXML
	private ImageView imgBrowserInstall;
	@FXML
	private ImageView imgCheckOk;
	@FXML
	private ImageView imgCheckBad;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public VBox getvBoxConfiguration() {
		return vBoxConfiguration;
	}

	public Button getBtnAnimeCollection() {
		return btnAnimeCollection;
	}

	public TextField getTxtPathAnimeCollection() {
		return txtPathAnimeCollection;
	}

	public Button getBtnMangaCollection() {
		return btnMangaCollection;
	}

	public TextField getTxtPathMangaCollection() {
		return txtPathMangaCollection;
	}

	public Button getBtnInstall() {
		return btnInstall;
	}

	public ImageView getImgBrowserInstall() {
		return imgBrowserInstall;
	}

	public Label getLblInstalled() {
		return lblInstalled;
	}

	public StackPane getSpMainPane() {
		return spMainPane;
	}

	public ImageView getImgCheckBad() {
		return imgCheckBad;
	}

	public ImageView getImgCheckOk() {
		return imgCheckOk;
	}

}
