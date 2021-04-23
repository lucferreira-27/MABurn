package com.lucas.ferreira.maburn.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.effects.Animations;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CollectionMenuController implements Initializable {
	private static final String ICON_PATH = "icons/";

	@FXML
	private AnchorPane apMenu;

//	@FXML
//	private BorderPane bpMenu;

	@FXML
	private ImageView imgMenu;

	@FXML
	private ImageView imgStorage;

	@FXML
	private ImageView imgFilter;

	@FXML
	private ImageView imgVariant;

	@FXML
	private ImageView imgCollectionItems;

	@FXML
	private ImageView imgCollectionPath;

	@FXML
	private ImageView imgHideitems;

	@FXML
	private ImageView imgFavoriteItems;

	@FXML
	private Label lblStorage;

	@FXML
	private Label lblFilter;

	@FXML
	private Label lblVariant;

	@FXML
	private Label lblCollectionItems;

	@FXML
	private Label lblCollectionPath;

	@FXML
	private Label lblHideItems;

	@FXML
	private Label lblFavoritesItems;

	private CollectionInterfaceController collectionController;

	private CollectionForm collectionForm;
	private ConfigForm configForm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		XmlCollectionOrchestrator collectionOrchestrator = new XmlCollectionOrchestrator();
		XmlConfigurationOrchestrator configOrchestrator = new XmlConfigurationOrchestrator();
		try {
			collectionForm = collectionOrchestrator.read();
			configForm = configOrchestrator.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		collectionController = (CollectionInterfaceController) Navigator.getMapNavigator().get(Interfaces.COLLECTION);

		collectionController.propertyFullLoaded().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue) {
				Animations animations = new Animations(apMenu);
				animations.moveMenuCollection(apMenu, 30, 230, 0.001);
				loadMenuIcons();
				setCollectionPathLabel();
				setHideItemsLabel();
				setFilterActive();
				setStorageLabel();
				setFavoriteLabel();
				setVariantLabel();
				listenImagesGridPaneSize();
				listenFilterActive();
			}
		});
//		collection = collectionController.getCollection();
//
//		Platform.runLater(() -> lblCollectionItems.setText(String.valueOf(collection.getItens().size())));

	}


	private void loadMenuIcons() {
		imgMenu.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "collection_menu.png")));

		imgCollectionPath.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "computer.png")));

		imgCollectionItems.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "collections.png")));

		imgFavoriteItems.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "favorite_black.png")));

		imgFilter.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "filter.png")));

		imgHideitems.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "hide_source.png")));

		imgStorage.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "storage.png")));

		imgVariant.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "variant.png")));
	}

	private void listenImagesGridPaneSize() {
		setFilterActive();
		collectionController.propertyItemTotal().addListener((obs, oldvalue, newvalue) -> {
			lblCollectionItems.setText(String.valueOf(newvalue.intValue()));
		});
	}

	private void listenFilterActive() {
		collectionController.getFilter().propertyActiveFilter().addListener((obs, oldvalue, newvalue) -> {
			lblFilter.setText(newvalue.name());
		});
	}

	private void setHideItemsLabel() {

		long blackListItems = collectionForm.getItems().stream().filter(item -> item.isBlackList()).count();

		lblHideItems.setText(String.valueOf(blackListItems));

	}

	private void setCollectionPathLabel() {
		Category category = collectionController.getCategory();
		String path = "Unknow";
		if (category == Category.ANIME)
			path = configForm.getAnimeConfig().getCollectionDestination();
		else if (category == Category.MANGA) {
			path = configForm.getMangaConfig().getCollectionDestination();
		}
		lblCollectionPath.setText(path);
	}
	private void setFilterActive() {
		
		String filter = collectionController.getFilter().propertyActiveFilter().getName();
		if(filter.isEmpty()) {
			filter = "None";
		}
		
		lblFilter.setText(filter);
	}
	private void setStorageLabel(){
		lblStorage.setText("0");
	}
	private void setFavoriteLabel() {
		lblFavoritesItems.setText("0");
	}
	private void setVariantLabel() {
		lblVariant.setText("0");
	}

}
