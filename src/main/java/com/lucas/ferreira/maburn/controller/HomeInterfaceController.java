package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class HomeInterfaceController implements Initializable {

	private MainInterfaceView mainView;
	private MainInterfaceController mainController = new MainInterfaceController();
	private HomeInterfaceView homeView;
	private Pane collectionMenu;
	private Button btnCollectionAnime = new Button();
	private Button btnCollectionManga = new Button();

	public HomeInterfaceController(MainInterfaceView mainView) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;

	}


	public void onClickOnAnime() {
		CustomLogger.log("Anime");

		new Thread(() -> {
			mainController.selectCollection(new AnimeCollection());
			ItemsInterfaceView itensView = new ItemsInterfaceView(mainController.getFutureCollection());
			itensView.loadMainInterfaceFX(mainView);
		}).start();

	}

	public void onClickOnManga() {
		CustomLogger.log("Manga");

		new Thread(() -> {
			try {
				mainController.selectCollection(new MangaCollection());
				ItemsInterfaceView itensView = new ItemsInterfaceView(mainController.getFutureCollection());
				itensView.loadMainInterfaceFX(mainView);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
