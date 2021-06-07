package com.lucas.ferreira.maburn.model.download.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;

public class ImageDownloadByChannel extends DownloadByChannel {

	private PageDownloadItemValues pageDownloadItemValues;

	public ImageDownloadByChannel(PageDownloadItemValues pageDownloadItemValues) {
		super(pageDownloadItemValues);
		this.pageDownloadItemValues = pageDownloadItemValues;
	}

	@Override
	protected void initTransfer() throws IOException {


		super.initTransfer();
		

		
		findImageMetadatas();

	}

	public void findImageMetadatas() {
		new Thread(() -> {
			waitUntilImageFileExist();
			readImageMetadatas();
		}).start();

	}

	private void waitUntilImageFileExist() {
		File file;
		do {
			file = new File(absolutePath);
		} while (!file.exists());
	}

	public ImageMetadata readImageMetadatas() {
		ReadMetadata readImageMetadata = new ReadImageMetadata();
		try {
			return (ImageMetadata) readImageMetadata.readTargetMetada(absolutePath);

		} catch (FileNotFoundException | FileMetadataNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public PageDownloadItemValues getPageDownloadItemValues() {
		return pageDownloadItemValues;
	}

}
