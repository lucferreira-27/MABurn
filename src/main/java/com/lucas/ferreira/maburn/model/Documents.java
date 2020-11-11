package com.lucas.ferreira.maburn.model;

import java.io.File;

public class Documents {
	public static void createDocumentFolders() {
		String local = ConfigurationReaderModel.DOCUMENTS_LOCAL;
		if (!(new File(local).exists())) {
			File fileLocal = new File(local);
			fileLocal.mkdirs();
		}
		File folderDocuments = new File(local + "//Documents");
		File folderFiles = new File(local + "//Files");

		folderDocuments.mkdirs();
		folderFiles.mkdirs();

	}
}
