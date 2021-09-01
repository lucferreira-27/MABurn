package com.lucas.ferreira.maburn.testing;

import com.lucas.ferreira.maburn.model.browser.BrowserFilesLocal;
import com.lucas.ferreira.maburn.model.browser.Platform;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class BrowserFilesLocalTest {
	XmlConfigurationOrchestrator configurationOrchestrator = new XmlConfigurationOrchestrator();

	@Test
	public void testGetBrowserLocalFromWindowsIfBrowserPathDefault() throws Exception {
		changeBrowserPathToDefault();
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = "AppData\\Local\\ms-playwright\\";
		String result = browserFilesLocal.getLocal(Platform.WINDOWS_64);
		assertTrue(result.contains(storage));

	}

	@Test
	public void testGetBrowserLocalFromWindowsIfBrowserPathMaburn() throws Exception {
		changeBrowserPathToMaburn();
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = Documents.BROWSER_LOCAL;
		String result = browserFilesLocal.getLocal(Platform.WINDOWS_64);
		assertTrue(result.contains(storage));

	}

	@Test
	public void testGetBrowserLocalFromLinuxIfBrowserPathDefault() throws Exception {
		changeBrowserPathToDefault();

		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = "~/Library/Caches/ms-playwright/";
		String result = browserFilesLocal.getLocal(Platform.LINUX);
		assertTrue(result.contains(storage));

	}

	@Test
	public void testGetBrowserLocalFromLinuxIfBrowserPathMaburn() throws Exception {
		changeBrowserPathToMaburn();
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = Documents.BROWSER_LOCAL;
		String result = browserFilesLocal.getLocal(Platform.LINUX);
		assertTrue(result.contains(storage));

	}

	@Test
	public void testGetBrowserLocalFromMacIfBrowserPathDefault() throws Exception {
		changeBrowserPathToDefault();

		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = "~/.cache/ms-playwright/";
		String result = browserFilesLocal.getLocal(Platform.MAC);
		assertTrue(result.contains(storage));

	}

	@Test
	public void testGetBrowserLocalFromMacIfBrowserPathMaburn() throws Exception {
		changeBrowserPathToMaburn();
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String storage = Documents.BROWSER_LOCAL;
		String result = browserFilesLocal.getLocal(Platform.MAC);
		
		assertTrue(result.contains(storage));

	}

	private void changeBrowserPathToDefault() throws IOException {

		String browserLocal = "DEFAULT";

		ConfigForm form = configurationOrchestrator.read();
		form.getGeralConfigForm().setBrowserLocal(browserLocal);
		configurationOrchestrator.write(form);

	}

	private void changeBrowserPathToMaburn() throws  IOException {

		String browserLocal = Documents.DOCUMENTS_LOCAL + "\\Browser";

		ConfigForm form = configurationOrchestrator.read();
		form.getGeralConfigForm().setBrowserLocal(browserLocal);
		configurationOrchestrator.write(form);

	}
}
