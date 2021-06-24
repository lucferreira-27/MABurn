package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FetchCard implements Initializable{
	@FXML
	private StackPane root;
	@FXML
	private Label labelItemName;
	@FXML
	private Label labelFetchState;
	@FXML
	private Label labelItemUrl;
	
	@FXML
	private ImageView imageViewFetchIcon;
	@FXML
	private ImageView imageViewLinkIcon;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public StackPane getRoot() {
		return root;
	}
	public Label getLabelItemName() {
		return labelItemName;
	}
	public Label getLabelFetchState() {
		return labelFetchState;
	}
	public Label getLabelItemUrl() {
		return labelItemUrl;
	}
	public ImageView getImageViewFetchIcon() {
		return imageViewFetchIcon;
	}
	public ImageView getImageViewLinkIcon() {
		return imageViewLinkIcon;
	}
	
	
}
