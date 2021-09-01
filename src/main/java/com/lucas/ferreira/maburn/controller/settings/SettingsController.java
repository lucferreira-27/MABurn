package com.lucas.ferreira.maburn.controller.settings;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.BrowserFilesLocal;
import com.lucas.ferreira.maburn.model.browser.BrowserInstallerLaunch;
import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.CheckBrowserFiles;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

public class SettingsController implements Initialize {
	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
		LOGGER.info("Click on [Anime Path]");

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
		LOGGER.info("Click on [Manga Path]");

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
		LOGGER.config("Click on [Reinstall]");

		install();

	}

	public void onClickReinstall() {
		LOGGER.config("Click on [Reinstall]");
		reinstall();

	}

	private void reinstall() {
		new Thread(() -> {
			BrowserInstallerController controller = openBrowserInstaller();
			try {
				controller.reinstall(Browsers.FIREFOX, Browsers.FFMPEG);
			} catch (BrowserInstallerException e) {
				LOGGER.severe("Error on Reinstall: " + e.getMessage());

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
				LOGGER.severe("Error on Install: " + e.getMessage());
				e.printStackTrace();
			}

		}).start();
	}

	private BrowserInstallerController openBrowserInstaller() {
		BrowserInstallerLaunch launch = new BrowserInstallerLaunch();
		try {
			 BrowserInstallerController controller = launch.openBrowserInstaller(settingsModel.getSpMainPane());
			 controller.addOnClose(() ->{
				 changeIconCheck();
				 initializePrefInstallation();
				 return null;
			 });
			 return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
