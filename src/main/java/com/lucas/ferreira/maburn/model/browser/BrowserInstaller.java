package com.lucas.ferreira.maburn.model.browser;

import java.io.IOException;

import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.ZipModel;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;

public class BrowserInstaller {
	private PlaywrightDownload playwrightDownload = new PlaywrightDownload();
	private BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
	private UserSystem userSystem = new UserSystem();
	private ZipModel zipModel;
//	private final static Browsers BROWSER_DEFAULT = Browsers.FIREFOX;

	public InstallerProcess install(Browsers browser) throws BrowserInstallerException {
		try {
			String local = browserFilesLocal.getLocal(browser, userSystem.getUserPlataform());
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

		fileDownloadValues.getDownloadProgressState().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == DownloadProgressState.COMPLETED) {
				fileExtractValues.setPath(fileExtractValues.getPath() + fileDownloadValues.getName().get());
				extractBrowserFolder(installerProcess);
						
			} else if (newvalue == DownloadProgressState.FAILED || newvalue == DownloadProgressState.CANCELED) {
				installerProcess.getProcessFailed().set(true);

			}
		});
		return installerProcess;
	}

	private void extractBrowserFolder(InstallerProcess installerProcess) {
		try {
			zipModel = new ZipModel(installerProcess.getFileExtractValues());
			zipModel.unzipFile();
		} catch (IOException e) {
			e.printStackTrace();
			installerProcess.getProcessFailed().set(true);
			return;
		}
		installerProcess.getProcessSuccess().set(true);
	}
}
