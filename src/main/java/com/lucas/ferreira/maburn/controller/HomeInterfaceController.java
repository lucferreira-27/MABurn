package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
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

	private static final String TEMPLATE_PATH = "template/home/";

	@FXML
	private ImageView imgAnimes;

	@FXML
	private ImageView imgMangas;

	@FXML
	private ImageView imgSettings;

	@FXML
	private ImageView imgDownloads;

	@FXML
	private ImageView imgBugReport;

	@FXML
	private ImageView imgCalendar;

	public HomeInterfaceController() {
		// TODO Auto-generated constructor stub

	}

	@FXML
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

	@FXML
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

	@FXML
	public void onClickOnDownlods() {

		navigator.open(Interfaces.DOWNLOADS);

	}

	@FXML
	public void onClickOnSettings() {
		navigator.open(Interfaces.CONFIGURATION);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			imgAnimes.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_01.png")));
			imgAnimes.hoverProperty().addListener((event) -> {
				if (imgAnimes.isHover())
					imgAnimes.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_01.png")));
				else
					imgAnimes.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_01.png")));

			});

			imgMangas.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_02.png")));
			imgMangas.hoverProperty().addListener((event) -> {
				if (imgMangas.isHover())
					imgMangas.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_02.png")));
				else
					imgMangas.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_02.png")));

			});
			imgCalendar.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_03.png")));
			imgCalendar.hoverProperty().addListener((event) -> {
				if (imgCalendar.isHover())
					imgCalendar.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_03.png")));
				else
					imgCalendar
							.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_03.png")));

			});

			imgDownloads.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_05.png")));
			imgDownloads.hoverProperty().addListener((event) -> {
				if (imgDownloads.isHover())
					imgDownloads.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_05.png")));
				else
					imgDownloads
							.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_05.png")));

			});
			imgSettings.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_04.png")));
			imgSettings.hoverProperty().addListener((event) -> {
				if (imgSettings.isHover())
					imgSettings.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_04.png")));
				else
					imgSettings
							.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_04.png")));

			});
			imgBugReport.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_06.png")));
			imgBugReport.hoverProperty().addListener((event) -> {
				if (imgBugReport.isHover())
					imgBugReport.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_red_06.png")));
				else
					imgBugReport
							.setImage(new Image(Resources.getResourceAsStream(TEMPLATE_PATH + "banner_white_06.png")));

			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Category getCategory() {
		return category;
	}

}
