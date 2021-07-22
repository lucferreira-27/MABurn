package com.lucas.ferreira.maburn.controller.settings;

import java.io.File;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerModel;
import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.ContainerBoxLoad;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.BrowserFilesLocal;
import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.CheckBrowserFiles;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Containers;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.ShadeLayer;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class SettingsController implements Initialize {

	private XmlConfigurationOrchestrator configurationOrchestrator = new XmlConfigurationOrchestrator();
	private SettingsModel settingsModel;

	private ConfigForm configForm;
	private static final String ICON_PATH = "icons/";

	public SettingsController(SettingsModel settingsModel) {
		this.settingsModel = settingsModel;

	}

	@Override
	public void initialize() throws InitializeExcpetion {
		try {
			configForm = configurationOrchestrator.read();

			if (isPathDefine(Category.MANGA)) {
				String path = configForm.getTitleConfigFormByCategory(Category.MANGA).getCollectionDestination();
				settingsModel.getTxtPathMangaCollection().setText(path);
			}
			if (isPathDefine(Category.ANIME)) {
				String path = configForm.getTitleConfigFormByCategory(Category.ANIME).getCollectionDestination();
				settingsModel.getTxtPathAnimeCollection().setText(path);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		initializePrefInstallation();
		initializeButtons();
		initializeIcons();

	}

	private void initializeIcons() {
		new Icon(settingsModel.getImgBrowserInstall(), new IconConfig(ICON_PATH, Icons.BROWSER_INSTALL_ICON_SETTINGS));
		changeIconCheck();
	}

	private void changeIconCheck() {
		
		CheckBrowserFiles checkBrowserFiles = new CheckBrowserFiles();

		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		boolean available = checkBrowserFiles.hasBrowserFilesAvailable(
				browserFilesLocal.getLocal(new UserSystem().getUserPlataform()), Browsers.FIREFOX);
		Icon iconCheckBad = new Icon(settingsModel.getImgCheckBad(),
				new IconConfig(ICON_PATH, Icons.BROWSER_CHECK_BAD));
		iconCheckBad.setToolTip("Not Installed");
		Icon iconCheckOk = new Icon(settingsModel.getImgCheckOk(), new IconConfig(ICON_PATH, Icons.BROWSER_CHECK_OK));
		iconCheckOk.setToolTip("Installed");
		
		if (!available) {
			iconCheckBad.setVisible(true);
			iconCheckOk.setVisible(false);
		} else {
			iconCheckOk.setVisible(true);
			iconCheckBad.setVisible(false);
		}
	}

	private void initializePrefInstallation() {
		CheckBrowserFiles checkBrowserFiles = new CheckBrowserFiles();
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		boolean available = checkBrowserFiles.hasBrowserFilesAvailable(
				browserFilesLocal.getLocal(new UserSystem().getUserPlataform()), Browsers.FIREFOX);
		if (available) {
			settingsModel.getBtnInstall().setText("Reinstall");
			settingsModel.getBtnInstall().setOnMouseClicked(event -> onClickReinstall());

		}

	}

	private void initializeButtons() {
		settingsModel.getBtnAnimeCollection().setOnMouseClicked(event -> onClickAnimeCollectionPath());
		settingsModel.getBtnMangaCollection().setOnMouseClicked(event -> onClickMangaCollectionPath());
		settingsModel.getBtnInstall().setOnMouseClicked(event -> onClickInstall());
	}

	private boolean isPathDefine(Category category) {

		try {

			String path = configForm.getTitleConfigFormByCategory(category).getCollectionDestination();
			if (path == null || path.isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public void onClickAnimeCollectionPath() {
		CustomLogger.log("Anime Path");

		File pathDestination = DirectoryModel
				.selectDirectory(settingsModel.getvBoxConfiguration().getScene().getWindow());

		if (pathDestination == null) {
			return;
		}
		String path = pathDestination.getAbsolutePath();
		settingsModel.getTxtPathAnimeCollection().setText(path);
		configForm.getTitleConfigFormByCategory(Category.ANIME).setCollectionDestination(path);
		configurationOrchestrator.write(configForm);

	}

	public void onClickMangaCollectionPath() {
		CustomLogger.log("Manga Path");

		File pathDestination = DirectoryModel
				.selectDirectory(settingsModel.getvBoxConfiguration().getScene().getWindow());
		if (pathDestination == null) {
			return;
		}

		String path = pathDestination.getAbsolutePath();
		settingsModel.getTxtPathMangaCollection().setText(path);
		configForm.getTitleConfigFormByCategory(Category.MANGA).setCollectionDestination(path);
		configurationOrchestrator.write(configForm);

	}

	public void onClickInstall() {

		install();

//		try {
//			BrowserInstaller browserInstaller = new BrowserInstaller();
//			browserInstaller.install(Browsers.FIREFOX);
//			
//			
//		} catch (BrowserInstallerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void onClickReinstall() {
		System.out.println("Reinstall");
		reinstall();

	}

	private void reinstall() {
		new Thread(() -> {
			BrowserInstallerController controller = openBrowserInstaller();
			try {
				controller.reinstall(Browsers.FIREFOX, Browsers.FFMPEG);
			} catch (BrowserInstallerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}

	private void install() {
		new Thread(() -> {
			BrowserInstallerController controller = openBrowserInstaller();
			try {
				controller.install(Browsers.FIREFOX, Browsers.FFMPEG);
			} catch (BrowserInstallerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}

	private BrowserInstallerController openBrowserInstaller() {
		ContainerBoxLoad<BorderPane> containerBoxLoad = new ContainerBoxLoad<BorderPane>();
		Containers containers = Containers.BROWSE_INSTALLER;
		AnchorPane anchorPane = new AnchorPane();
		ShadeLayer shadeLayer = new ShadeLayer(anchorPane);
		BrowserInstallerModel browserInstallerModel = (BrowserInstallerModel) containers.getModelInitialize();
		try {
			BorderPane borderPane = containerBoxLoad.load(Containers.BROWSE_INSTALLER);
			BrowserInstallerController browserInstallerController = (BrowserInstallerController) containerBoxLoad
					.setContainerController(Containers.BROWSE_INSTALLER, browserInstallerModel);
			browserInstallerController.onClose(() -> {
				settingsModel.getSpMainPane().getChildren().remove(borderPane);
				settingsModel.getSpMainPane().getChildren().remove(shadeLayer.getRecShade());
				changeIconCheck();
				initializePrefInstallation();
				return null;
			});
			StackPane.setAlignment(borderPane, Pos.CENTER);

			shadeLayer.show();
			Platform.runLater(() -> {
				settingsModel.getSpMainPane().getChildren().add(shadeLayer.getRecShade());
				settingsModel.getSpMainPane().getChildren().add(borderPane);
			});

			return browserInstallerController;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
