package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.ZipModel;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.enums.InstallationState;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class BrowserInstaller {
	private final PlaywrightDownload playwrightDownload = new PlaywrightDownload();
	private final BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
	private final UserSystem userSystem = new UserSystem();
	//	private final static Browsers BROWSER_DEFAULT = Browsers.FIREFOX;

	public InstallerProcess install(Binaries browser) throws BrowserInstallerException {
		try {
			String local = browserFilesLocal.getLocal(userSystem.getUserPlataform());
			FileExtractValues fileExtractValues = new FileExtractValues(local);
			FileDownloadValues fileDownloadValues = download(local, browser);

			return installProcess(fileDownloadValues, fileExtractValues);

		} catch (Exception e) {
			throw new BrowserInstallerException(e.getMessage());
			
		}
	}

	private FileDownloadValues download(String local, Binaries browser) throws Exception {
		return playwrightDownload.download(local, browser);
	}

	private InstallerProcess installProcess(FileDownloadValues fileDownloadValues,
			FileExtractValues fileExtractValues) {
		InstallerProcess installerProcess = new InstallerProcess(fileExtractValues, fileDownloadValues);
		installerProcess.getInstallationState().set(InstallationState.DOWNLOADING);

		fileDownloadValues.getDownloadProgressState().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == DownloadProgressState.COMPLETED) {
				fileExtractValues.setPath(fileExtractValues.getPath() + fileDownloadValues.getName().get());
				extractBrowserFolder(installerProcess);

			} else if (newvalue == DownloadProgressState.FAILED || newvalue == DownloadProgressState.CANCELED) {
				installerProcess.getInstallationState().set(InstallationState.FAILED);

			}
		});
		return installerProcess;
	}

	private void extractBrowserFolder(InstallerProcess installerProcess) {
		try {
			installerProcess.getInstallationState().set(InstallationState.EXTRACTING);
			ZipModel zipModel = new ZipModel(installerProcess.getFileExtractValues());
			zipModel.unzipFile();
		} catch (IOException e) {
			e.printStackTrace();
			installerProcess.getInstallationState().set(InstallationState.FAILED);
			return;
		}
		installerProcess.getInstallationState().set(InstallationState.COMPLETE);

	}

	public void stopInstallation() {
		playwrightDownload.stop();
		removeAllFiles();
	}

	public void removeAllFiles() {
		String local = browserFilesLocal.getLocal(new UserSystem().getUserPlataform()).replaceAll("\\\\$", "");
		Path file = Paths.get(local);
		try {

			Files.walk(file).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
