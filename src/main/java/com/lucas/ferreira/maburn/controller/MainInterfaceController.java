package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.lucas.ferreira.maburn.model.ConfigurationReaderModel;
import com.lucas.ferreira.maburn.model.DocumentConfigurationModel;
import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainInterfaceController implements Initializable {
	private MainInterfaceView mainView;
	private MainLoader mainLoader;
	private CollectionLoader collectionLoader;
	private Collections collection;
	private InitializeModel initialize;
	private ConfigurationReaderModel configReader = new ConfigurationReaderModel();
	private DocumentConfigurationModel doc;
	private Future<?> futureCollection;

	public MainInterfaceController(MainInterfaceView mainView, Collections collection) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;
		this.collection = collection;
		this.initialize = new InitializeModel(new HelperInterfaceView());
		this.doc = new DocumentConfigurationModel(configReader.getDocumentConfiguration());
	}

	public MainInterfaceController() {
		// TODO Auto-generated constructor stub
		this.mainView = new MainInterfaceView();
		this.initialize = new InitializeModel(new HelperInterfaceView());
		initialize.boot();
		this.doc = new DocumentConfigurationModel(configReader.getDocumentConfiguration());
	}

	public void run() {
		mainView.initAndShowGUI();

	}

	public void selectCollection(Collections collections) {

		mainLoader = new MainLoader(collections);
		futureCollection = mainLoader.loadCollection(doc.getPath(collections.getCategory())); // return a future collection, (collection
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
