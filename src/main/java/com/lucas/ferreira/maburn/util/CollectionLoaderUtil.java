package com.lucas.ferreira.maburn.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Element;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadImageServiceModel;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class CollectionLoaderUtil {
	static CollectionDatasReader dataReader = new CollectionDatasReader();
	static DocumentCollectionReader docCollectionReader = new DocumentCollectionReader(
			dataReader.getDocumentCollectionDates());

	public static boolean isRequiredFilesAvailable(CollectionItem item) {
		ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
		try {
			thumbnailLoader.findImage();
			return true;
		} catch (ThumbnailLoadException e) {
			// TODO: handle exception
			return false;
		}

	}

	public static void getRequiredFiles(CollectionItem item) {
		// TODO Auto-generated method stub
		DownloadImageServiceModel downloadImageServiceModel = new DownloadImageServiceModel(item.getImageUrl(),
				new File(item.getImageLocal().substring(0, item.getImageLocal().lastIndexOf("."))));

		try {
			downloadImageServiceModel.download();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isCreateItemInDocument(String itemPath, Category category) {

		if (!docCollectionReader.xmlContains(itemPath, category)) {
			return false;
		}
		return true;

	}

	public static boolean isAllDatesFilled(CollectionItem item) {
		List<Element> els = docCollectionReader.getElementsInItem(item);

		return !els.stream().anyMatch(el -> el == null);
	}
}
