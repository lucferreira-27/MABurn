package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.ConfigurationReaderModel;
import com.lucas.ferreira.maburn.model.DocumentConfigurationModel;
import com.lucas.ferreira.maburn.model.enums.Category;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class HelperInterfaceController implements Initializable {

	@FXML
	private VBox vBoxConfiguration;
	@FXML
	private Button btnAnimeCollection;
	@FXML
	private TextField txtPathAnimeCollection;
	@FXML
	private Button btnMangaCollection;
	@FXML
	private TextField txtPathMangaCollection;

	private ConfigurationReaderModel config = new ConfigurationReaderModel();
	private DocumentConfigurationModel docConfiguration;

	public HelperInterfaceController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (isPathDefine(Category.MANGA)) {
			docConfiguration = new DocumentConfigurationModel(config.getDocumentConfiguration());
			String path = docConfiguration.getPath(Category.MANGA);
			txtPathMangaCollection.setText(path);
		}
		if (isPathDefine(Category.ANIME)) {
			docConfiguration = new DocumentConfigurationModel(config.getDocumentConfiguration());
			String path = docConfiguration.getPath(Category.ANIME);
			txtPathAnimeCollection.setText(path);
		}
	}

	private boolean isPathDefine(Category category) {
		docConfiguration = new DocumentConfigurationModel(config.getDocumentConfiguration());
		try {
			String path = docConfiguration.getPath(category);
			if (path.isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public void onClickAnimeCollectionPath() {
		System.out.println("Anime Path");

		DirectoryChooser directoryChooser = new DirectoryChooser();
		File pathDestination = directoryChooser.showDialog(vBoxConfiguration.getScene().getWindow());
		if (pathDestination == null) {
			return;
		}
		String path = pathDestination.getAbsolutePath();
		txtPathAnimeCollection.setText(path);
		docConfiguration = new DocumentConfigurationModel(config.getDocumentConfiguration());
		docConfiguration.setPath(path, Category.ANIME);
	}

	public void onClickMangaCollectionPath() {
		System.out.println("Manga Path");


		DirectoryChooser directoryChooser = new DirectoryChooser();

		File pathDestination = directoryChooser.showDialog(vBoxConfiguration.getScene().getWindow());
		if (pathDestination == null) {
			return;
		}

		String path = pathDestination.getAbsolutePath();
		txtPathMangaCollection.setText(path);
		docConfiguration = new DocumentConfigurationModel(config.getDocumentConfiguration());

		docConfiguration.setPath(path, Category.MANGA);

	}

}
