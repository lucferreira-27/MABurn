package com.lucas.ferreira.maburn.model.media;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class ChapterDirecotoryModel {

	private DownloadInfo downloadInfo;
	private ChapterDownloadValues chapterDownloadValues;

	public ChapterDirecotoryModel(DownloadInfo downloadInfo, ChapterDownloadValues chapterDownloadValues) {
		this.downloadInfo = downloadInfo;
		this.chapterDownloadValues = chapterDownloadValues;
	}

	public void openFile() {
		String diretory = downloadInfo.getPath() + "\\" + downloadInfo.getFilename() + "\\"
				+ chapterDownloadValues.getListItemsDownloadValues().get(0).getName().get();
		

		try {
			System.out.println("Opening: " + diretory);
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFolder() {
		String diretory = downloadInfo.getPath() + "\\" + downloadInfo.getFilename();

		try {
			System.out.println("Opening: " + diretory);
			DirectoryModel.openDirectory(diretory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}