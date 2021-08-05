package com.lucas.ferreira.maburn.controller.download.title;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.controller.home.ModelInterface;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DownloadTitleInQueueModal implements ModelInterface {
	
	@FXML
	private HBox hbMainPane;

	@FXML
	private ImageView imgTitleCover;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblDownloads;

	@FXML
	private ImageView imgDownloads;

	@FXML
	private Label lblCompleted;

	@FXML
	private ImageView imgCompleted;

	@FXML
	private Label lblErros;

	@FXML
	private ImageView imgErros;

	@FXML
	private ImageView imgPause;

	@FXML
	private ImageView imgStop;

	@FXML
	private Label lblTime;

	@FXML
	private ImageView imgTime;

	@FXML
	private Label lblFolder;

	@FXML
	private ImageView imgFolder;

	@FXML
	private ImageView imgLaunch;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public Node getRoot() {
		return hbMainPane;
	}

	public HBox getHbMainPane() {
		return hbMainPane;
	}

	public ImageView getImgTitleCover() {
		return imgTitleCover;
	}

	public Label getLblTitle() {
		return lblTitle;
	}

	public Label getLblDownloads() {
		return lblDownloads;
	}

	public ImageView getImgDownloads() {
		return imgDownloads;
	}

	public Label getLblCompleted() {
		return lblCompleted;
	}

	public ImageView getImgCompleted() {
		return imgCompleted;
	}

	public Label getLblErros() {
		return lblErros;
	}

	public ImageView getImgErros() {
		return imgErros;
	}

	public ImageView getImgPause() {
		return imgPause;
	}

	public ImageView getImgStop() {
		return imgStop;
	}

	public Label getLblTime() {
		return lblTime;
	}

	public ImageView getImgTime() {
		return imgTime;
	}

	public Label getLblFolder() {
		return lblFolder;
	}

	public ImageView getImgFolder() {
		return imgFolder;
	}

	public ImageView getImgLaunch() {
		return imgLaunch;
	}

}
