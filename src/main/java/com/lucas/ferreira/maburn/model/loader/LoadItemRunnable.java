package com.lucas.ferreira.maburn.model.loader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.w3c.dom.Element;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.SaveCollection;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadImageServiceModel;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.ItemCreater;
import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class LoadItemRunnable implements Callable<CollectionItem> {
	private StringProperty loadProperty;
	private String destination;
	private CollectionItem item;
	private CollectionDatasReader dataReader;
	private DocumentCollectionReader docCollectionReader;
	private SaveCollection save;

	private Category category;

	public LoadItemRunnable(String destination, Category category, StringProperty loadProperty) {
		// TODO Auto-generated constructor stub
		this.destination = destination;
		this.category = category;
		this.loadProperty = loadProperty;
	
			dataReader = new CollectionDatasReader();
			docCollectionReader = new DocumentCollectionReader(dataReader.getDocumentCollectionDates());

			save = new SaveCollection(dataReader.getDocumentCollectionDates());
		

	}

	@Override
	public CollectionItem call() throws Exception {
		// TODO Auto-generated method stub
		return loadItem(destination);
	}

	private CollectionItem loadItem(String itemPath) {
		// TODO Auto-generated method stub
		CollectionItem item;
		if (category == Category.ANIME) {
			item = loadAnime(itemPath);
			return item;
		} else if (category == Category.MANGA) {
			item = loadManga(itemPath);
			return item;
		}

		return null;
	}

	public void newBuilder() {
		docCollectionReader = new DocumentCollectionReader(dataReader.getDocumentCollectionDates());

		save = new SaveCollection(dataReader.getDocumentCollectionDates());
	}

	private synchronized MangaDownloaded loadManga(String mangaPath) {
		MangaDownloaded item = new MangaDownloaded();
		item.setCollections(item.getMangaCollection());
		item.setDestination(mangaPath); // It is important to define the destination because it will be used to locate
		// the item in the document
		ItemCreater<MangaDownloaded> itemCreater = new MangaItemCreate(item.getMangaCollection());
		if (isCreateItemInDocument(mangaPath, Category.MANGA) && isAllDatesFilled(item)) {
			item = (MangaDownloaded) save.loadDatas(item);
			if (!isRequiredFilesAvailable(item)) {
				getRequiredFiles(item);
				item = itemCreater.createItem(mangaPath);
				return item;
			} else {
				return item;
			}
		} else {
			if (isCreateItemInDocument(mangaPath, Category.MANGA)) {
				save.deleteData(item);
			} else {
				loadProperty.set("Creating " + mangaPath);
				item = itemCreater.createItem(mangaPath); // Create item and write in document
			}
		}

		return item;
	}

	private synchronized AnimeDownloaded loadAnime(String animePath) {
		AnimeDownloaded item = new AnimeDownloaded();
		item.setCollections(item.getAnimeCollection());
		item.setDestination(animePath); // It is important to define the destination because it will be used to locate
										// the item in the document
		ItemCreater<AnimeDownloaded> itemCreater = new AnimeItemCreate(item.getAnimeCollection());

		if (isCreateItemInDocument(animePath, Category.ANIME) && isAllDatesFilled(item)) {
			// System.out.println(item.getTitleDataBase() + " Loading ...");
			item = (AnimeDownloaded) save.loadDatas(item);
			// System.out.println(item.getTitleDataBase() + " Loaded!");
			if (!isRequiredFilesAvailable(item)) {
				getRequiredFiles(item);
				System.out.println("> REQUIRED " + item.getTitleDataBase() + " | " + item.getImageLocal());

			}
		} else {
			if (isCreateItemInDocument(animePath, Category.ANIME)) {

				save.deleteData(item);

			} else {
				loadProperty.set("Creating " + animePath);
				item = itemCreater.createItem(animePath); // Create item and write in document

			}
		}

		return item;
	}

	private void getRequiredFiles(CollectionItem item) {
		// TODO Auto-generated method stub
		File imageFile = null;
		if(item.getCategory() == Category.ANIME) {
			imageFile = new File(Documents.THUMBNAILS_LOCAL_ANIMES);
		}else if(item.getCategory() == Category.MANGA) {
			imageFile = new File(Documents.THUMBNAILS_LOCAL_MANGAS);
		}
		DownloadImageServiceModel downloadImageServiceModel = new DownloadImageServiceModel(item.getImageUrl(),
				new File(imageFile + "\\" + item.getTitleDataBase()));

		try {
			downloadImageServiceModel.download();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isCreateItemInDocument(String itemPath, Category category) {
		synchronized (this) {

			if (!docCollectionReader.xmlContains(itemPath, category)) {
				return false;
			}
			return true;
		}
	}

	private boolean isAllDatesFilled(CollectionItem item) {
		List<Element> els = docCollectionReader.getElementsInItem(item);

		return !els.stream().anyMatch(el -> el == null);
	}

	private boolean isRequiredFilesAvailable(CollectionItem item) {
		ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
		try {
			thumbnailLoader.findImage();
			return true;
		} catch (ThumbnailLoadException e) {
			// TODO: handle exception
			return false;
		}

	}

}