package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.lucas.ferreira.maburn.exceptions.CollectionLoaderException;
import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainInterfaceController implements Initializable {
	private MainInterfaceView mainView;
	private AlertWindowView alertWindown = new AlertWindowView();
	private MainLoader mainLoader;
	private CollectionLoader collectionLoader;
	private Collections collection;
	private InitializeModel initialize;
	private ConfigurationReader configReader = new ConfigurationReader();
	private DocumentConfiguration doc;
	private Future<?> futureCollection;

	public MainInterfaceController(MainInterfaceView mainView, Collections collection) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;
		this.collection = collection;
		this.initialize = new InitializeModel(new HelperInterfaceView());
		this.doc = new DocumentConfiguration(configReader.getDocumentConfiguration());
	}

	public MainInterfaceController() {
		// TODO Auto-generated constructor stub
		this.mainView = new MainInterfaceView();
		this.initialize = new InitializeModel(new HelperInterfaceView());
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
			
			alertWindown.errorAlert("ERROR", "Collection error",
					collections.getCategory() + " collection path is not definide");
			throw new CollectionLoaderException(collections.getCategory() + " collection path is not definide");

		}
		futureCollection = mainLoader.loadCollection(doc.getPath(collections.getCategory())); // return a future
																								// collection,
																								// (collection
		// promise)

	}

	public CollectionItem sessionSelectItem(String category, int id, Collections collection) {
		mainLoader = new MainLoader(collection);
		System.out.println(collection.getDestination());
		CollectionItem item = mainLoader.loadSelectItem(id);
		return item;
	}

	public Collections getCollection() {
		return collection;
	}

	public Future<?> getFutureCollection() {
		return futureCollection;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
