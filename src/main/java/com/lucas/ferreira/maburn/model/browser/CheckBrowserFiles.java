package com.lucas.ferreira.maburn.model.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CheckBrowserFiles {

	public boolean hasBrowserFilesAvailable(String local, Browsers browser) {
		File fileLocal = new File(local);

		if (!fileLocal.exists()) {
			return false;
		}
		List<String> results = listAllFilesNamesInFolder(fileLocal);
		if (results.size() <= 0) {
			return false;
		}
		boolean found = searchBrowserName(browser, results);
		return found;
	}

	private boolean searchBrowserName(Browsers browser, List<String> results) {
		return results.stream().filter((result) -> result.contains(browser.name().toLowerCase())).findFirst()
				.isPresent();
	}

	private List<String> listAllFilesNamesInFolder(File fileLocal) {
		List<String> results = new ArrayList<String>();

		File[] files = fileLocal.listFiles();

		for (File file : files) {
			if (file.isFile() || file.isDirectory()) {
				results.add(file.getName());
			}
		}

		return results;
	}

}
