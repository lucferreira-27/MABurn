package com.lucas.ferreira.maburn.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Resources {

	private ClassLoader classLoader = getClass().getClassLoader();
	private static FileSystem resourceFileSystem;
	
	
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
		resPath.toFile();
		String str = new String(java.nio.file.Files.readAllBytes(resPath));
		return str;
	}

	public static Path getResourcePath(String resourceName) throws Exception {
		if (isJar()) {
			Map<String, String> env = new HashMap<>();
			URI uri = Resources.class.getClassLoader().getResource("resources/" + resourceName).toURI();
			String[] array = uri.toString().split("!");
			resourceFileSystem = FileSystems.newFileSystem(URI.create(array[0]), env);
			
			Path path = resourceFileSystem.getPath(array[1]);
			
			return path;
		}else {
			URI uri = Resources.class.getClassLoader().getResource(resourceName).toURI();
			return Paths.get(uri);
		}

	}

	private static boolean isJar() {
		return Resources.class.getResourceAsStream("/resources/") != null;
	}
	public static FileSystem getResourceFileSystem() {
		return resourceFileSystem;
	}
}
