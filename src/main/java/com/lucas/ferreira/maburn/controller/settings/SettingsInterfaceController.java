package com.lucas.ferreira.maburn.controller.settings;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SettingsInterfaceController implements Initializable {

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

	private XmlConfigurationOrchestrator configurationOrchestrator = new XmlConfigurationOrchestrator();

	private ConfigForm configForm;

	public SettingsInterfaceController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			configForm = configurationOrchestrator.read();

			if (isPathDefine(Category.MANGA)) {
				String path = configForm.getTitleConfigFormByCategory(Category.MANGA).getCollectionDestination();
				txtPathMangaCollection.setText(path);
			}
			if (isPathDefine(Category.ANIME)) {
				String path = configForm.getTitleConfigFormByCategory(Category.ANIME).getCollectionDestination();
				txtPathAnimeCollection.setText(path);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private boolean isPathDefine(Category category) {

		try {

			String path = configForm.getTitleConfigFormByCategory(category).getCollectionDestination();
			if (path == null || path.isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public void onClickAnimeCollectionPath() {
		CustomLogger.log("Anime Path");

		File pathDestination = DirectoryModel.selectDirectory(vBoxConfiguration.getScene().getWindow());

		if (pathDestination == null) {
			return;
		}
		String path = pathDestination.getAbsolutePath();
		txtPathAnimeCollection.setText(path);
		configForm.getTitleConfigFormByCategory(Category.ANIME).setCollectionDestination(path);
		configurationOrchestrator.write(configForm);

	}

	public void onClickMangaCollectionPath() {
		CustomLogger.log("Manga Path");

		File pathDestination = DirectoryModel.selectDirectory(vBoxConfiguration.getScene().getWindow());
		if (pathDestination == null) {
			return;
		}

		String path = pathDestination.getAbsolutePath();
		txtPathMangaCollection.setText(path);
		configForm.getTitleConfigFormByCategory(Category.MANGA).setCollectionDestination(path);
		configurationOrchestrator.write(configForm);

	}

}
