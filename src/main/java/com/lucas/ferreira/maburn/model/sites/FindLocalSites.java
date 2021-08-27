package com.lucas.ferreira.maburn.model.sites;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import com.lucas.ferreira.maburn.util.Resources;

public class FindLocalSites {
	public Path findAll() throws Exception {
		String local = "scraping/sites";
		Path path = Resources.getResourcePath(local);

		return path;

	}

	public Path find(String site) throws Exception {
		String local = "scraping/sites/" + site;
		Path path = Resources.getResourcePath(local);

		return path;

	}

	public void findInFiles(File file) {

	}

}
