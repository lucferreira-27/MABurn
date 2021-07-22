package com.lucas.ferreira.maburn.controller.title.download.installer;

import java.text.DecimalFormat;
import java.util.function.Supplier;

import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.ModelInitialiable;
import com.lucas.ferreira.maburn.model.browser.BrowserInstaller;
import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.InstallerProcess;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.enums.InstallationState;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.application.Platform;

public class BrowserInstallerController implements Initialize {

	private BrowserInstallerModel browserInstallerModel;
	private BrowserInstaller browserInstaller = new BrowserInstaller();
	private Supplier<Void> onClose;
	private static final String ICON_PATH = "icons/";

	public BrowserInstallerController(BrowserInstallerModel browserInstallerModel) {
		this.browserInstallerModel = browserInstallerModel;
		initialize();
	}

	public BrowserInstallerController(ModelInitialiable model) {
		this.browserInstallerModel = (BrowserInstallerModel) model;
		initialize();
	}

	public void initialize() {

		initializeIcons();
		initializeButtons();
	}

	private void initializeButtons() {
		browserInstallerModel.getBtnAction().setOnMouseClicked(event -> cancel());

	}

	public void install(Browsers... browsers) throws BrowserInstallerException {

		for (Browsers browser : browsers) {
			InstallerProcess installerProcess = initInstallation(browser);
			waitUntilInstallationFinish(installerProcess);
		}

	}
	public void reinstall(Browsers... browsers) throws BrowserInstallerException {
		browserInstaller.removeAllFiles();
		for (Browsers browser : browsers) {
			InstallerProcess installerProcess = initInstallation(browser);
			waitUntilInstallationFinish(installerProcess);
		}
		finishProcess();

	}
	private void waitUntilInstallationFinish(InstallerProcess installerProcess) {
		while (installerProcess.getInstallationState().get() != InstallationState.COMPLETE
				&& installerProcess.getInstallationState().get() != InstallationState.FAILED) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private InstallerProcess initInstallation(Browsers browser) throws BrowserInstallerException {
		InstallerProcess installerProcess = browserInstaller.install(browser);
		Platform.runLater(() -> {
			browserInstallerModel.getLblStatus().setText(installerProcess.getInstallationState().get().name());

		});

		installerProcess.getInstallationState().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				browserInstallerModel.getLblStatus().setText(newvalue.name());
				
				if(newvalue == InstallationState.EXTRACTING) {
					browserInstallerModel.getPbPogress().progressProperty()
					.unbind();
					browserInstallerModel.getLblProgres().setText("0%");

					browserInstallerModel.getPbPogress().progressProperty().bind(installerProcess.getFileExtractValues().getExtractingProgress());
					return;
				}
				
				if (newvalue == InstallationState.COMPLETE) {
					browserInstallerModel.getLblProgres().setText("100%");
					
				}

			});
		});
		DecimalFormat decimalFormat = new DecimalFormat();

		installerProcess.getFileDownloadValues().getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				decimalFormat.applyLocalizedPattern("####.##");
				double value = newvalue.doubleValue() * 100;
				if ((int) value >= 99) {
					value = 99;
				}
				String formatProgress = decimalFormat.format(value);
				browserInstallerModel.getLblProgres().setText(formatProgress + "%");

			});
		});
		installerProcess.getFileExtractValues().getExtractingProgress().addListener((obs, oldvalue, newvalue) -> {
			
			Platform.runLater(() -> {
				decimalFormat.applyLocalizedPattern("####.##");
				double value = newvalue.doubleValue() * 100;
				if ((int) value >= 99) {
					value = 99;
				}
				String formatProgress = decimalFormat.format(value);
				browserInstallerModel.getLblProgres().setText(formatProgress + "%");

			});
		});

		browserInstallerModel.getPbPogress().progressProperty()
		.bind(installerProcess.getFileDownloadValues().getDownloadProgress());
		return installerProcess;
	}

	private void finishProcess() {
		Platform.runLater(() -> {
			browserInstallerModel.getBtnAction().setText("Close");
			browserInstallerModel.getBtnAction().setOnMouseClicked(event -> close());
		});

	}

	private void initializeIcons() {
		Icon iconClose = new Icon(browserInstallerModel.getImgClose(), new IconConfig(ICON_PATH, Icons.CLOSE));
		iconClose.setProperties(img -> {
			close();
			cancel();
		});
		new Icon(browserInstallerModel.getImgBrowserInstall(), new IconConfig(ICON_PATH, Icons.BROWSER_INSTALL_ICON));

	}

	public void cancel() {
		browserInstaller.stopInstallation();
	}

	private void close() {
		onClose.get();

	}

	public void onClose(Supplier<Void> onClose) {
		this.onClose = onClose;
	}

}
