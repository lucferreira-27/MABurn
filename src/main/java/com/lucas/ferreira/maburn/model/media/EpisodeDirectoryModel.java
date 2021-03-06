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
		String diretory = downloadInfo.getRoot() + "\\" + downloadInfo.getFilename() +  "." + downloadInfo.getPrefFiletype().getName();
		
		try {
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}
	public void openFolder() {
		String diretory = downloadInfo.getRoot();
		
		try {
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}
	
	
}
