package com.lucas.ferreira.maburn.model.loader;

import java.util.List;
import java.util.concurrent.Callable;

import org.w3c.dom.Element;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.documents.SaveCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.ItemCreater;
import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;

public class LoadItemRunnable implements Callable<CollectionItem> {
	private String destination;
	private CollectionItem item;
	private CollectionDatasReader dataReader;
	private DocumentCollectionReader docCollectionReader;
	private SaveCollection save;

	private Category category;

	public LoadItemRunnable(String destination, Category category) {
		// TODO Auto-generated constructor stub
		this.destination = destination;
		this.category = category;
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

	private MangaDownloaded loadManga(String mangaPath) {
		MangaDownloaded item = new MangaDownloaded();
		item.setCollections(item.getMangaCollection());
		item.setDestination(mangaPath); // It is important to define the destination because it will be used to locate
		// the item in the document
		ItemCreater<MangaDownloaded> itemCreater = new MangaItemCreate(item.getMangaCollection());

		if (isCreateItemInDocument(mangaPath, Category.MANGA) && isAllDatesFilled(item)) {
			item = (MangaDownloaded) save.loadDatas(item);
			if (!isRequiredFilesAvailable(item)) {
				save.deleteData(item);
				System.out.println("> DELETE");
				item = itemCreater.createItem(mangaPath);
				System.out.println("> ADD AND CREATEED");
				return item;
			}
			System.out.println("> ADD AND LOADED");
		} else {
			if (isCreateItemInDocument(mangaPath, Category.MANGA)) {
				save.deleteData(item);
				System.out.println("> DELETE");
			} else {
				item = itemCreater.createItem(mangaPath); // Create item and write in document
				System.out.println("> ADD AND CREATEED");
			}
		}

		return item;
	}

	private AnimeDownloaded loadAnime(String animePath) {
		AnimeDownloaded item = new AnimeDownloaded();
		item.setCollections(item.getAnimeCollection());
		item.setDestination(animePath); // It is important to define the destination because it will be used to locate
										// the item in the document
		ItemCreater<AnimeDownloaded> itemCreater = new AnimeItemCreate(item.getAnimeCollection());

		if (isCreateItemInDocument(animePath, Category.ANIME) && isAllDatesFilled(item)) {
			item = (AnimeDownloaded) save.loadDatas(item);
			if (!isRequiredFilesAvailable(item)) {
				save.deleteData(item);
				System.out.println("> DELETE");
				save.loadDatas(item);
			}
			System.out.println("> ADD AND LOADED");
		} else {
			if (isCreateItemInDocument(animePath, Category.ANIME)) {
				save.deleteData(item);
				System.out.println("> DELETE");
			} else {
				item = itemCreater.createItem(animePath); // Create item and write in document
				System.out.println("> ADD AND CREATEED");
			}
		}

		return item;
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

		System.out.println(els.stream().anyMatch(el -> el == null));

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
