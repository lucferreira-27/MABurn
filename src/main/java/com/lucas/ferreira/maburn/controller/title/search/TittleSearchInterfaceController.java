package com.lucas.ferreira.maburn.controller.title.search;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.controller.collection.CollectionInterfaceController;
import com.lucas.ferreira.maburn.controller.home.ModelInterface;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class TittleSearchInterfaceController implements ModelInterface {

	private static final long serialVersionUID = 4013518062488454167L;
	private Collections collections;
	private Navigator navigator = new Navigator();

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

	public TittleSearchInterfaceController() {
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		loadTitleDatas();

	}

	@FXML
	public void onClickButtonBack() {
		
		navigator.back();
	}

	@FXML
	public void onClickButtonAdd() {

		String dest = collections.getDestination() + "\\" + collections.getSelectedItem().getTitleFileName();
		File newItem = new File(dest);
		if (!newItem.exists()) {
			
			newItem.mkdir();
		}
	}

	private void loadTitleDatas() {

		CollectionInterfaceController collectionController = (CollectionInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.COLLECTION);
		collections = collectionController.getCollectionGridPane().getCollection();
		CollectionTitle item = collections.getSelectedItem();

		lblTitle.setText(item.getTitleDataBase());
		imageViewTitle.setImage(item.getImage());
		imageViewTitle.setOnMouseClicked(event -> {

			onClickImageViewTitle();

		});
		loadInformation(item);

	}

	public void loadInformation(CollectionTitle item) {
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
		CollectionTitle item = collections.getSelectedItem();
		Desktop desk = Desktop.getDesktop();
		try {
			desk.browse(new URI(item.getDataBaseUrl()));
		} catch (IOException | URISyntaxException e) {
			 
			e.printStackTrace();
		}
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
