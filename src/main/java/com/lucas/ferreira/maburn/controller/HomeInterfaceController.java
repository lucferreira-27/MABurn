package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeInterfaceController implements Initializable {
	private Navigator navigator = new Navigator();
	private Category category;

	private DataFetcher dataFetcher;

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

		category = Category.ANIME;
		navigator.open(Interfaces.COLLECTION);

//		CustomLogger.log("Anime");
//		data = new DataFetcher(Category.ANIME);
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.submit(data);
//		executorService.shutdown();
		// ItemsInterfaceView itensView = new ItemsInterfaceView(data);
		// itensView.loadMainInterfaceFX();

	}

	public void onClickOnManga() {
		
		category = Category.MANGA;
		navigator.open(Interfaces.COLLECTION);

//		CustomLogger.log("Manga");
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.submit(dataFetcher);
//		executorService.shutdown();
//		FXMLViewLoader loader = new FXMLViewLoader();
//		loader.loadInterface("ItensViewFXML.fxml", new CollectionInterfaceController());
		// ItemsInterfaceView itensView = new ItemsInterfaceView(data);
		// itensView.loadMainInterfaceFX();

	}

	public void onClickOnDownlods() {

		navigator.open(Interfaces.DOWNLOADS);

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
	public Category getCategory() {
		return category;
	}

}
