package com.lucas.ferreira.maburn.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

	private ClassLoader classLoader = getClass().getClassLoader();

	public static InputStream getResourceAsStream(String resource) {

		InputStream input = Resources.class.getResourceAsStream("/resources/" + resource);
		if (input == null) {
			// if we are load file within IDE
			input = Resources.class.getClassLoader().getResourceAsStream(resource);
		}

		return input;
	}

	public String getResourceAsString(String resource) throws IOException, URISyntaxException {
		URL url = classLoader.getResource(resource);
		Path resPath = Paths.get(url.toURI());
		String str = new String(java.nio.file.Files.readAllBytes(resPath));
		return str;
	}

	public Path getResourceAsPath(String resource) throws IOException, URISyntaxException {
		URL url = classLoader.getResource(resource);
		Path resPath = Paths.get(url.toURI());
		return resPath;
		
	}

}
