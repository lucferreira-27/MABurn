package com.lucas.ferreira.maburn.controller.download;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.controller.home.ModelInterface;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DownloadQueueModal implements ModelInterface{
	
	@FXML
	private ScrollPane spItemsImagesScroll;
	@FXML
	private VBox vbTitles;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
	public VBox getVbTitles() {
		return vbTitles;
	}



}
