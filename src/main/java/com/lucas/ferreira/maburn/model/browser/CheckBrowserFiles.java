package com.lucas.ferreira.maburn.model.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CheckBrowserFiles {

	public boolean hasBrowserFilesAvailable(String local, Binaries browser) {
		File fileLocal = new File(local);

		if (!fileLocal.exists()) {
			return false;
		}
		List<String> results = listAllFilesNamesInFolder(fileLocal);
		if (results.size() <= 0) {
			return false;
		}
		return searchBrowserName(browser, results);
	}

	private boolean searchBrowserName(Binaries browser, List<String> results) {
		return results.stream().anyMatch((result) -> result.contains(browser.name().toLowerCase()));
	}

	private List<String> listAllFilesNamesInFolder(File fileLocal) {
		List<String> results = new ArrayList<>();

		File[] files = fileLocal.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile() || file.isDirectory()) {
					results.add(file.getName());
				}
			}
		}
		return results;


	}

}
