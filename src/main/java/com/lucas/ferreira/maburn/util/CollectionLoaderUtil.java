package com.lucas.ferreira.maburn.util;

import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.download.DownloadImageServiceModel;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class CollectionLoaderUtil {


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


}
