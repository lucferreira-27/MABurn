package com.lucas.ferreira.maburn.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.databases.Database;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.TitleSearchInterfaceView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TittleSearchInterfaceController implements Initializable {
	private ItemsInterfaceView itensView;
	private Collections collections;
	
	
	@FXML
	private ImageView imageViewTitle;
	@FXML
	private Button btnAdd;

	@FXML
	private Label lblTitle;
	@FXML
	private TextArea txtAreaSynopsis;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblPublishedDate;
	@FXML
	private Label lblAverageRating;

	
	public TittleSearchInterfaceController(TitleSearchInterfaceView titleView,
			ItemsInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.itensView = itensView;
		this.collections = itensView.getController().getCollection();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadTitleDatas();

	}
	@FXML
	public void onClickButtonBack() {
		ItemsInterfaceView itensView = this.itensView;
		System.out.println("Back: "+collections);
		//itensView.setCollections(collections);
		
		itensView.loadMainInterfaceFX();
		
	}
	@FXML
	public void onClickButtonAdd() {
			
		String dest = collections.getDestination() + "\\" + collections.getActualItem().getTitleFileName();
		File newItem = new File(dest);
		if(!newItem.exists()) {
			System.out.println("Mkdir: " + dest);
			newItem.mkdir();
		}
	}

	private void loadTitleDatas() {

		CollectionItem item = collections.getActualItem();

		
		
		lblTitle.setText(item.getTitleDataBase());
		imageViewTitle.setImage(item.getImage());
		imageViewTitle.setOnMouseClicked(event -> {

			onClickImageViewTitle();

		});
		loadInformation(item);


	}

	public void loadInformation(CollectionItem item) {
		Database database = new KitsuDatabase();
		CollectDatas datas = database.read(item.getId(), item.getCategory());
		Platform.runLater(() -> {
			txtAreaSynopsis.setText(datas.getSynopsis());
			txtAreaSynopsis.setVisible(true);
			lblStatus.setText(lblStatus.getText() + ": " + datas.getStatus());
			lblStatus.setVisible(true);
			lblPublishedDate.setText(lblPublishedDate.getText() + ": " + datas.getPublishedDate());
			lblPublishedDate.setVisible(true);
			lblAverageRating.setText(lblAverageRating.getText() + ": " + datas.getAvaregeRating());
			lblAverageRating.setVisible(true);
		});
	}

	public void onClickImageViewTitle() {
		CollectionItem item = collections.getActualItem();
		Desktop desk = Desktop.getDesktop();
		try {
			desk.browse(new URI(item.getDataBaseUrl()));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	



}
