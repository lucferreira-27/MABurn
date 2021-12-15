package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.BrowserFilesLocal;
import com.lucas.ferreira.maburn.model.browser.Binaries;
import com.lucas.ferreira.maburn.model.browser.CheckBrowserFiles;

public class CheckBrowserFilesTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	@Test
	public void testHasBrowserFilesAvailableShouldPass() throws IOException {
		CheckBrowserFiles checkBrowserFiles = new CheckBrowserFiles();

		String local = temp.newFolder().getAbsolutePath() + "\\Folder" ;
		createBrowserFiles(local, Binaries.FIREFOX.name().toLowerCase());
		
		boolean result = checkBrowserFiles.hasBrowserFilesAvailable(local, Binaries.FIREFOX);
		assertTrue(result);
	}
	@Test
	public void testHasBrowserFilesAvailableShouldNot() throws IOException {
		CheckBrowserFiles checkBrowserFiles = new CheckBrowserFiles();

		String local = temp.newFolder().getAbsolutePath();
		
		
		boolean result = checkBrowserFiles.hasBrowserFilesAvailable(local, Binaries.FIREFOX);
		assertTrue(!result);
	}

	
	
	private void createBrowserFiles(String local, String filename) {
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		browserFilesLocal.getLocal(new UserSystem().getUserPlataform());
		File file = new File(local + "\\" +filename);
		file.mkdirs();
		
	}
}
