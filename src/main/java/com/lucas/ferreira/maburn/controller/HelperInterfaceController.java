package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
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

	private ConfigurationReader config = new ConfigurationReader();
	private DocumentConfiguration docConfiguration;

	public HelperInterfaceController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (isPathDefine(Category.MANGA)) {
			docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());
			String path = docConfiguration.getPath(Category.MANGA);
			txtPathMangaCollection.setText(path);
		}
		if (isPathDefine(Category.ANIME)) {
			docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());
			String path = docConfiguration.getPath(Category.ANIME);
			txtPathAnimeCollection.setText(path);
		}
	}

	private boolean isPathDefine(Category category) {
		docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());
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

		File pathDestination = DirectoryModel.selectDirectory(vBoxConfiguration.getScene().getWindow());

		if (pathDestination == null) {
			return;
		}
		String path = pathDestination.getAbsolutePath();
		txtPathAnimeCollection.setText(path);
		docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());
		docConfiguration.setPath(path, Category.ANIME);
	}

	public void onClickMangaCollectionPath() {
		System.out.println("Manga Path");

		
		File pathDestination = DirectoryModel.selectDirectory(vBoxConfiguration.getScene().getWindow());
		if (pathDestination == null) {
			return;
		}

		String path = pathDestination.getAbsolutePath();
		txtPathMangaCollection.setText(path);
		docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());

		docConfiguration.setPath(path, Category.MANGA);

	}

}
