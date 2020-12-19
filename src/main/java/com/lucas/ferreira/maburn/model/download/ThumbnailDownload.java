package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadImageServiceModel;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.concurrent.Task;

public class ThumbnailDownload extends Task<Void> {

	private CollectionItem item;
	private DownloadImageServiceModel modelService;
	public ThumbnailDownload(CollectionItem item) {
		// TODO Auto-generated constructor stub
		this.item = item;
	}

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		download();
		return null;
	}

	public File download() throws IOException {

		File file = null;
		switch (item.getCategory()) {

		case ANIME:
			file = new File(Documents.THUMBNAILS_LOCAL_ANIMES + item.getTitleFileName());
			break;
		case MANGA:
			file = new File(Documents.THUMBNAILS_LOCAL_MANGAS + item.getTitleFileName());

			break;
		default:
			break;
		}
		modelService = new DownloadImageServiceModel(item.getImageUrl(), file);
		
		return modelService.download();
	
	}

}
