package com.lucas.ferreira.maburn.model.sites;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.lucas.ferreira.maburn.util.Resources;

public class FindLocalSites {
	public File[] findAll() throws IOException, URISyntaxException {
		Resources resources = new Resources();
		String local = "scraping\\sites";

		String pathname = resources.getResourceAsPath(local).toFile().getAbsolutePath();

		File file = new File(pathname);
		return file.listFiles();
		
	}
	public File find(String site) throws IOException, URISyntaxException {
		Resources resources = new Resources();
		String local = "scraping\\sites\\" + site;

		String pathname = resources.getResourceAsPath(local).toFile().getAbsolutePath();

		File file = new File(pathname);
		return file;
		
	}



	public void findInFiles(File file) {

	}

}
