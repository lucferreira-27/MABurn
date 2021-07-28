package com.lucas.ferreira.maburn.controller.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeInterfaceController implements ModelInterface {

	private static final long serialVersionUID = -6957084711836026682L;
	private Navigator navigator = new Navigator();
	private Category category;
	private XmlConfigurationOrchestrator xmlConfigurationOrchestrator = new XmlConfigurationOrchestrator();	
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
		

	}

	@FXML
	public void onClickOnAnime() {
		
		
		ConfigForm configForm;
		try {
			configForm = xmlConfigurationOrchestrator.read();
			String destination = configForm.getAnimeConfig().getCollectionDestination();
			
			if(destination == null || destination.isEmpty()) {
				AlertWindowView.errorAlert("ERROR", "Anime Collection", "Anime collection destination need be set");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		category = Category.ANIME;
		navigator.open(Interfaces.COLLECTION);

	}

	@FXML
	public void onClickOnManga() {
		ConfigForm configForm;
		try {
			configForm = xmlConfigurationOrchestrator.read();
			String destination = configForm.getMangaConfig().getCollectionDestination();
			
			if(destination == null || destination.isEmpty()) {
				AlertWindowView.errorAlert("ERROR", "Manga Collection", "Anime collection destination need be set");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		category = Category.MANGA;
		navigator.open(Interfaces.COLLECTION);



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
			
			e.printStackTrace();
		}
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
