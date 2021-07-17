package com.lucas.ferreira.maburn.model.images;

import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.model.ImageLoaderModel;
import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.download.DownloadImageServiceModel;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class ThumbnailController extends Task<Void> {

	private CollectionTitle item;
	private DownloadImageServiceModel modelService;
	public ThumbnailController(CollectionTitle item) {
		
		this.item = item;
	}
	public ThumbnailController(ListItemForm itemForm) {
		
		if(itemForm.getCategory() == Category.ANIME) {
			item = new AnimeDownloaded();
		}
		if(itemForm.getCategory() == Category.MANGA) {
			item = new MangaDownloaded();
		}
		item.setImageUrl(itemForm.getImageUrl());
		item.setTitleDataBase(itemForm.getTitleDatabase());
	}

	@Override
	protected Void call() throws Exception {
		
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
	public Image load() throws IOException {

		ImageLoaderModel loader = new ImageLoaderModel();
		Image image =loader.loadImageByUrl(item.getImageUrl());
		return image;
	
	}

}
