package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.Binaries;
import com.lucas.ferreira.maburn.model.browser.PlaywrightDownload;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;

public class PlaywrightDownloadTest {
	@Test
	public void testDownloadBrowserFirefoxAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Binaries.FIREFOX);
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
	public void testDownloadFfmpegCompleteAndStorageInLocal() {
		PlaywrightDownload playwrightDownload = new PlaywrightDownload();
		String local = System.getProperty("java.io.tmpdir");

		try {
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Binaries.FFMPEG_COMPLETE);
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
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Binaries.CHROMIUM);
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
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Binaries.WEBKIT);
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
			FileDownloadValues fileDownloadValues = playwrightDownload.download(local, Binaries.FFMPEG);
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
			System.out.println(fileDownloadValues.getDownloadProgressState().get());
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
