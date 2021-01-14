package com.lucas.ferreira.maburn.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HomeInterfaceController implements Initializable {

	private MainInterfaceView mainView;
	private MainInterfaceController mainController = new MainInterfaceController();
	private HomeInterfaceView homeView;
	private Pane collectionMenu;

	@FXML
	private Button btnCollectionAnime = new Button();

	@FXML
	private Button btnCollectionManga = new Button();

	@FXML
	private Button btnDownloads = new Button();

	@FXML
	private Button btnCalendar = new Button();

	public HomeInterfaceController() {
		// TODO Auto-generated constructor stub

	}

	public void onClickOnAnime() {
		CustomLogger.log("Anime");

		new Thread(() -> {
			mainController.selectCollection(new AnimeCollection());
			ItemsInterfaceView itensView = new ItemsInterfaceView(mainController.getFutureCollection());
			itensView.loadMainInterfaceFX();
		}).start();

	}

	public void onClickOnManga() {
		CustomLogger.log("Manga");

		new Thread(() -> {
			try {
				mainController.selectCollection(new MangaCollection());
				ItemsInterfaceView itensView = new ItemsInterfaceView(mainController.getFutureCollection());
				itensView.loadMainInterfaceFX();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {


			Image imgAnime = new Image(Resources.getResourceAsStream("icons/anime.png"));
			Image imgManga = new Image(Resources.getResourceAsStream("icons/manga.png"));
			Image imgDownloads = new Image(Resources.getResourceAsStream("icons/download.png"));
			Image imgCalendar = new Image(Resources.getResourceAsStream("icons/calendar.png"));

			btnCollectionAnime.setGraphic(new ImageView(imgAnime));
			btnCollectionManga.setGraphic(new ImageView(imgManga));
			btnDownloads.setGraphic(new ImageView(imgDownloads));
			btnCalendar.setGraphic(new ImageView(imgCalendar));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
