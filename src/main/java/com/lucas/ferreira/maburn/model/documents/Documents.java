package com.lucas.ferreira.maburn.model.documents;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class Documents {
	
	public final static String DOCUMENTS_LOCAL = System.getProperty("user.home")
			+ "\\MABurn";
	
	public final static String DATA_LOCAL = Documents.DOCUMENTS_LOCAL + "\\Documents\\ColletionsDatas.xml";
	
	public final static String CONFIG_LOCAL = Documents.DOCUMENTS_LOCAL + "\\Documents\\Config.xml";
	
	public final static String FILES_LOCAL = DOCUMENTS_LOCAL + "\\Files";
	
	public final static String THUMBNAILS_LOCAL = FILES_LOCAL + "\\Thumbnails";
	
	public final static String THUMBNAILS_LOCAL_ANIMES = THUMBNAILS_LOCAL + "\\Animes\\";
	
	public final static String THUMBNAILS_LOCAL_MANGAS = THUMBNAILS_LOCAL + "\\Mangas\\";
	public final static String BROWSER_LOCAL = DOCUMENTS_LOCAL + "\\Browser\\";
	public final static String SCRIPT_LOCAL = DOCUMENTS_LOCAL + "\\Scripts\\";

	public static void createDocumentFolders() {
		
		String local = DOCUMENTS_LOCAL;
		if (!(new File(local).exists())) {
			File fileLocal = new File(local);
			fileLocal.mkdirs();
		}
		File folderDocuments = new File(local + "//Documents");
		File folderFiles = new File(local + "//Files");
		
		File folderThumbnailsAnimes = new File(local + "//Files//Thumbnails//Animes");
		File folderThumbnailsMangas = new File(local + "//Files//Thumbnails//Mangas");
		
		folderDocuments.mkdirs();
		folderFiles.mkdirs();
		folderThumbnailsAnimes.mkdirs();
		folderThumbnailsMangas.mkdirs();
		
	}
	

}
