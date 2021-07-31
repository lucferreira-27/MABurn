package com.lucas.ferreira.mangaburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.PlaywrightDownload;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;

public class PlaywrightDownloadTest {
	@Test
	public void testDownloadBrowserFirefoxAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Browsers.FIREFOX);
			waitUntilDownloadStartOrTimeOut(fileDownloadValues);
			File file = new File(local + fileDownloadValues.getName().get());
			file.deleteOnExit();
			assertTrue(file.exists());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDownloadBrowserChromiumAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Browsers.CHROMIUM);
			waitUntilDownloadStartOrTimeOut(fileDownloadValues);

			File file = new File(local + fileDownloadValues.getName().get());
			file.deleteOnExit();
			assertTrue(file.exists());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDownloadBrowserWebKitAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Browsers.WEBKIT);
			waitUntilDownloadStartOrTimeOut(fileDownloadValues);

			File file = new File(local + fileDownloadValues.getName().get());
			file.deleteOnExit();
			assertTrue(file.exists());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDownloadBrowserFfmpegAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Browsers.FFMPEG);
			waitUntilDownloadStartOrTimeOut(fileDownloadValues);

			File file = new File(local + fileDownloadValues.getName().get());
			file.deleteOnExit();
			assertTrue(file.exists());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void waitUntilDownloadStartOrTimeOut(FileDownloadValues fileDownloadValues) throws TimeoutException {
		long timeout = 15l;
		long start = System.currentTimeMillis();

		while (fileDownloadValues.getDownloadProgressState().get() != DownloadProgressState.DOWNLOADING) {
			long end = System.currentTimeMillis();
			long now = (start - end) / 1000;
			if (now >= timeout) {
				throw new TimeoutException("Download timeout");
			}

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
