package com.lucas.ferreira.maburn.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ResourcesFile {
	public static List<Path> listFiles(Path path) throws IOException {
		return Files.walk(path, 1).filter(Files::isRegularFile).collect(Collectors.toList());
	}

	public static List<Path> listFolders(Path path) throws IOException {

		return 	Files.walk(path, 1).filter(Files::isDirectory).collect(Collectors.toList());

	}

	public static List<Path> listAll(Path path) throws IOException {

		return Files.walk(path, 1).collect(Collectors.toList());
	}

	public static InputStream pathToInputStream(Path path) {
		String abs = path.toAbsolutePath().toString();
		abs = abs.replaceAll(".*:", "");
		abs = abs.replace("\\", "/");

		if (abs.contains("resources/"))
			abs = abs.replaceAll(".*\\/resources\\/", "");
		else if(abs.contains("classes"))
			abs = abs.substring(("classes".length() + 1) + abs.indexOf("classes"));
		return Resources.getResourceAsStream(abs);
	}
}
