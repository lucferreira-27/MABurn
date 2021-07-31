package com.lucas.ferreira.maburn.controller.menu;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController implements Initializable {

	private Navigator navigator = new Navigator();

	private static final String ICON_PATH = "icons/";

	@FXML
	private ImageView imgHome;

	@FXML
	private ImageView imgSettings;

	@FXML
	private ImageView imgDownloads;

	public MenuController() {
		

	}

	public boolean active() {
		return true;

	}

	@FXML
	public void onClickButtonHome() {
		Navigator.getInterfacesList().clear();
		navigator.open(Interfaces.HOME);

	}

	@FXML
	public void onClickButtonConfiguration() {
		Navigator.getInterfacesList().clear();

		navigator.open(Interfaces.CONFIGURATION);

	}

	@FXML
	public void onClickButtonDownloads() {
		Navigator.getInterfacesList().clear();

		navigator.open(Interfaces.DOWNLOADS);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		

		imgHome.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "home_white.png")));
		imgHome.hoverProperty().addListener((event) -> {
			if (imgHome.isHover())
				imgHome.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "home_black.png")));
			else
				imgHome.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "home_white.png")));

		});
		imgSettings.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "settings_white.png")));
		imgSettings.hoverProperty().addListener((event) -> {
			if (imgSettings.isHover())
				imgSettings.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "settings_black.png")));
			else
				imgSettings.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "settings_white.png")));

		});
		imgDownloads.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "download_white.png")));
		imgDownloads.hoverProperty().addListener((event) -> {
			if (imgDownloads.isHover())
				imgDownloads.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "download_black.png")));
			else
				imgDownloads.setImage(new Image(Resources.getResourceAsStream(ICON_PATH + "download_white.png")));

		});

	}

}
