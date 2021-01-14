package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.exceptions.CollectionLoaderException;
import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.Initializable;

public class MainInterfaceController implements Initializable {
	private MainInterfaceView mainView;
	private MainLoader mainLoader;
	private Collections collection;
	private InitializeModel initialize;
	private ConfigurationReader configReader = new ConfigurationReader();
	private DocumentConfiguration doc;
	private CollectionLoader futureCollection;

	public MainInterfaceController(MainInterfaceView mainView, Collections collection) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;
		this.collection = collection;
		this.initialize = new InitializeModel();
		this.doc = new DocumentConfiguration(configReader.getDocumentConfiguration());
	}

	public MainInterfaceController() {
		// TODO Auto-generated constructor stub
		this.mainView = new MainInterfaceView();
		this.initialize = new InitializeModel();
		initialize.boot();
		this.doc = new DocumentConfiguration(configReader.getDocumentConfiguration());
	}

	public void run() {
		mainView.initAndShowGUI();

	}

	public void selectCollection(Collections collections) throws CollectionLoaderException {

		mainLoader = new MainLoader(collections);
		String destination = doc.getPath(collections.getCategory());
		if (destination == null || destination.isEmpty()) {

			AlertWindowView.errorAlert("ERROR", "Collection error",
					collections.getCategory() + " collection path is not definide");
			throw new CollectionLoaderException(collections.getCategory() + " collection path is not definide");

		}
		// return a future collection,  (collection promise)
		futureCollection = mainLoader.loadCollection(doc.getPath(collections.getCategory())); 
		CustomLogger.log(futureCollection);
		
	}

	public CollectionItem sessionSelectItem(String category, int id, Collections collection) {
		mainLoader = new MainLoader(collection);
		CustomLogger.log(collection.getDestination());
		CollectionItem item = mainLoader.loadSelectItem(id);
		return item;
	}

	public Collections getCollection() {
		return collection;
	}

	public CollectionLoader getFutureCollection() {
		return futureCollection;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
