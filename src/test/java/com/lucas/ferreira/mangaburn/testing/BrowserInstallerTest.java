package com.lucas.ferreira.mangaburn.testing;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.BrowserInstaller;
import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.InstallerProcess;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;

public class BrowserInstallerTest {

	DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	@Test
	public void testBrowserInstallFirefox() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Browsers.FIREFOX);
		downloadRealTimeInfo.showInfoWithProgress(installerProcess.getFileDownloadValues());
		downloadRealTimeInfo.showInfoWithProgress(installerProcess.getFileExtractValues());

		while (installerProcess.getFileDownloadValues().getDownloadProgressState()
				.get() != DownloadProgressState.COMPLETED ) {
			if(installerProcess.getFileDownloadValues().getDownloadProgressState()
					.get() == DownloadProgressState.FAILED) {
				throw new Exception();
			}
			Thread.sleep(500);
		}
	
		while (!installerProcess.getFileExtractValues().getFinish().get()
				&& !installerProcess.getFileExtractValues().getFailed().get()) {
			
			Thread.sleep(500);
		}
	}
	@Test
	public void testBrowserInstallChromium() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Browsers.CHROMIUM);
		downloadRealTimeInfo.showInfoWithProgress(installerProcess.getFileDownloadValues());
		while (installerProcess.getFileDownloadValues().getDownloadProgressState()
				.get() != DownloadProgressState.COMPLETED ) {
			if(installerProcess.getFileDownloadValues().getDownloadProgressState()
					.get() == DownloadProgressState.FAILED) {
				throw new Exception();
			}
			Thread.sleep(500);
		}
		while (!installerProcess.getFileExtractValues().getFinish().get()
				&& !installerProcess.getFileExtractValues().getFailed().get()) {
			System.out.println(installerProcess.getFileExtractValues().toString());
			Thread.sleep(10);
		}


	}
	@Test
	public void testBrowserInstallWebKit() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Browsers.WEBKIT);
		downloadRealTimeInfo.showInfoWithProgress(installerProcess.getFileDownloadValues());
		while (installerProcess.getFileDownloadValues().getDownloadProgressState()
				.get() != DownloadProgressState.COMPLETED ) {
			if(installerProcess.getFileDownloadValues().getDownloadProgressState()
					.get() == DownloadProgressState.FAILED) {
				throw new Exception();
			}
			Thread.sleep(500);
		}
		while (!installerProcess.getFileExtractValues().getFinish().get()
				&& !installerProcess.getFileExtractValues().getFailed().get()) {
			System.out.println(installerProcess.getFileExtractValues().toString());
			Thread.sleep(10);
		}

	}
	@Test
	public void testBrowserInstallFfmpeg() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Browsers.FFMPEG);
		downloadRealTimeInfo.showInfoWithProgress(installerProcess.getFileDownloadValues());
		while (installerProcess.getFileDownloadValues().getDownloadProgressState()
				.get() != DownloadProgressState.COMPLETED ) {
			if(installerProcess.getFileDownloadValues().getDownloadProgressState()
					.get() == DownloadProgressState.FAILED) {
				throw new Exception();
			}
			Thread.sleep(500);
		}
		while (!installerProcess.getFileExtractValues().getFinish().get()
				&& !installerProcess.getFileExtractValues().getFailed().get()) {
			System.out.println(installerProcess.getFileExtractValues().toString());
			Thread.sleep(10);
		}

	}
}
