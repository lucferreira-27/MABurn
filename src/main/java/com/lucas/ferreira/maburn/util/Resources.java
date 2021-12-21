package com.lucas.ferreira.maburn.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Resources {

	private final ClassLoader classLoader = getClass().getClassLoader();
	private static FileSystem resourceFileSystem;
	
	
	public static InputStream getResourceAsStream(String resource) {

		InputStream input = Resources.class.getResourceAsStream("/resources" + resource);
		if (input == null) {
			// if we are load file within IDE
			if(!resource.matches("^\\/.*")){ // Need start with /
				resource = "/" + resource;
			}
			input = Resources.class.getResourceAsStream(resource);

		}

		return input;
	}

	public String getResourceAsString(String resource) throws IOException, URISyntaxException {
		URL url = Objects.requireNonNull(classLoader.getResource(resource));

		Path resPath = Paths.get(url.toURI());
		return new String(Files.readAllBytes(resPath));
	}

	public static Path getResourcePath(String resourceName) throws Exception {
		String prefix = resourceName;
		if(resourceName.contains("/")){
			 prefix = resourceName.split("/")[0];
		}
		System.out.println(prefix);
		URI uri = Objects.requireNonNull(Resources.class.getClassLoader().getResource(resourceName)).toURI();
		if (isResourceInJar(uri)) { //if we are load file within IDE
			Map<String, String> env = new HashMap<>();
			String[] array = uri.toString().split("!");
			resourceFileSystem = FileSystems.newFileSystem(URI.create(array[0]), env);
			Path temp = Files.createTempDirectory(prefix + "-");
			temp.toFile().deleteOnExit();
			Path path = resourceFileSystem.getPath(array[1]);
			ResourcesFile.copyDirectory(path, temp);
			resourceFileSystem.close();
			return temp;
		}else {

			return Paths.get(uri);
		}


	}

	private static boolean isResourceInJar(URI uri) throws URISyntaxException {
		return uri.toString().contains("!");
	}

	public static FileSystem getResourceFileSystem() {
		return resourceFileSystem;
	}
}
