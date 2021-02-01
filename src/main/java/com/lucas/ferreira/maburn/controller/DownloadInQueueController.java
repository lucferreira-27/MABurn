package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class DownloadInQueueController implements Initializable {
	private TitleDownload item;
	
	
	@FXML
	private Label lblTitleName;
	
	@FXML
	private Label lblTotal;
	
	@FXML
	private ImageView imgTitle;

	@FXML
	private ProgressBar pbProgress;

	@FXML
	private BorderPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		item = (TitleDownload) root.getUserData();
		
		lblTitleName.setText(item.getCollectionItem().getTitleDataBase());
		
		lblTotal.textProperty().bindBidirectional(item.getStrTotalProgressPropery());
		
		
		Platform.runLater(() -> {
			pbProgress.progressProperty().set(item.getTotalProgressPropery().doubleValue());
			item.getTotalProgressPropery().bindBidirectional(pbProgress.progressProperty());
		});
		
		try {
			InputStream stream = new FileInputStream(new File(item.getCollectionItem().getImageLocal()));
			imgTitle.setImage(new Image(stream));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	@FXML
	private void onClickOnButtonOpen() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
	}
	@FXML
	private void onClickOnButtonRemove() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
	}
	@FXML
	private void onClickOnButtonPause() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
	
	}

}
