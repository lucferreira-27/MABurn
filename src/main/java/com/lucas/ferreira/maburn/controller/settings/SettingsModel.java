package com.lucas.ferreira.maburn.controller.settings;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.controller.home.ModelInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SettingsModel implements ModelInterface {


	private static final long serialVersionUID = 3548154041118919878L;
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
	private CheckBox checkBrowserHeadless;
	@FXML
	private ComboBox<String> cbScrapingTimeOut;
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

	public CheckBox getCheckBrowserHeadless() {
		return checkBrowserHeadless;
	}

	public ComboBox<String> getCbScrapingTimeOut() {
		return cbScrapingTimeOut;
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
