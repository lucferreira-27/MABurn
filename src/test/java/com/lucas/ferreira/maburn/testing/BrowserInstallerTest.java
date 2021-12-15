package com.lucas.ferreira.maburn.testing;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.BrowserInstaller;
import com.lucas.ferreira.maburn.model.browser.Binaries;
import com.lucas.ferreira.maburn.model.browser.InstallerProcess;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;

public class BrowserInstallerTest {

	DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	@Test
	public void testBrowserInstallFirefox() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Binaries.FIREFOX);
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

		InstallerProcess installerProcess = browserInstaller.install(Binaries.CHROMIUM);
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
			Thread.sleep(10);
		}


	}
	@Test
	public void testBrowserInstallWebKit() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Binaries.WEBKIT);
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
			Thread.sleep(10);
		}

	}
	@Test
	public void testBrowserInstallFfmpeg() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Binaries.FFMPEG);
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
			Thread.sleep(10);
		}

	}
	@Test
	public void testBrowserInstallFfmpegComplete() throws Exception {
		BrowserInstaller browserInstaller = new BrowserInstaller();

		InstallerProcess installerProcess = browserInstaller.install(Binaries.FFMPEG_COMPLETE);
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
			Thread.sleep(10);
		}

	}
}
