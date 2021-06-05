package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileTypeDetection {
	
	
	public static boolean isAcceptType(String type) {
		return anyMathFound(type);
	}

	private static boolean anyMathFound(String type) {
		return Arrays.asList(FileTypeAccept.values()).stream()
				.anyMatch((fileTypeAccept) -> fileTypeAccept.getName().equals(type));
	}
	


}
