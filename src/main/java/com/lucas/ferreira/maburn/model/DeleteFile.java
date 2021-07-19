package com.lucas.ferreira.maburn.model;

import java.io.File;

public class DeleteFile {
	public DeleteFile() {

	}

	public void delete(String path) {

		File file = new File(path);

		file.delete();

	}
}
