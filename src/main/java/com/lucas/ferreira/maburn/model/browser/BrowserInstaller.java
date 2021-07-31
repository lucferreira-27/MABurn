package com.lucas.ferreira.maburn.model.browser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.ZipModel;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.enums.InstallationState;

public class BrowserInstaller {
	private PlaywrightDownload playwrightDownload = new PlaywrightDownload();
	private BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
	private UserSystem userSystem = new UserSystem();
	private ZipModel zipModel;
//	private final static Browsers BROWSER_DEFAULT = Browsers.FIREFOX;

	public InstallerProcess install(Browsers browser) throws BrowserInstallerException {
		try {
			String local = browserFilesLocal.getLocal(userSystem.getUserPlataform());
			FileExtractValues fileExtractValues = new FileExtractValues(local);
			FileDownloadValues fileDownloadValues = download(local, browser);

			InstallerProcess installerProcess = installProcess(fileDownloadValues, fileExtractValues);

			return installerProcess;

		} catch (Exception e) {
			throw new BrowserInstallerException(e.getMessage());
			
		}
	}

	private FileDownloadValues download(String local, Browsers browser) throws Exception {
		FileDownloadValues fileDownloadValues = playwrightDownload.download(local, browser);
		return fileDownloadValues;
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
			zipModel = new ZipModel(installerProcess.getFileExtractValues());
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
		System.out.println(local);
		Path file = Paths.get(local);
		try {

			Files.walk(file).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
