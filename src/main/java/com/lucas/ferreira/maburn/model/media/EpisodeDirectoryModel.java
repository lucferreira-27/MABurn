package com.lucas.ferreira.maburn.model.media;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class EpisodeDirectoryModel {
	
	private DownloadInfo downloadInfo;
	
	public EpisodeDirectoryModel(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}
	
	public void openFile() {
		String diretory = downloadInfo.getPath() + "\\" + downloadInfo.getFilename() +  "." + downloadInfo.getPrefFiletype().getName();
		
		try {
			System.out.println("Opening File: " + diretory);
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void openFolder() {
		String diretory = downloadInfo.getPath();
		
		try {
			System.out.println("Opening Folder: " + diretory);
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
