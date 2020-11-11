package com.lucas.ferreira.maburn.model.loader;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.CollectionDatasReaderModel;
import com.lucas.ferreira.maburn.model.DocumentCollectionReaderModel;
import com.lucas.ferreira.maburn.model.SaveCollectionModel;
import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.ItemCreater;
import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;

public class LoadItemRunnable implements Callable<CollectionItem> {
	private String destination;
	private CollectionItem item;
	private CollectionDatasReaderModel dataReader;
	private DocumentCollectionReaderModel docCollectionReader;
	private SaveCollectionModel save;

	private Category category;

	public LoadItemRunnable(String destination, Category category) {
		// TODO Auto-generated constructor stub
		this.destination = destination;
		this.category = category;
		dataReader = new CollectionDatasReaderModel();
		docCollectionReader = new DocumentCollectionReaderModel(dataReader.getDocumentCollectionDates());
		save = new SaveCollectionModel(dataReader.getDocumentCollectionDates());
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

	private Manga loadManga(String mangaPath) {
		Manga item = new Manga();
		item.setDestination(mangaPath); // It is important to define the destination because it will be used to locate
		// the item in the document
		ItemCreater<Manga> itemCreater = new MangaItemCreate((MangaCollection) item.getMangaCollection());
		if (!isCreateItemInDocument(mangaPath, Category.MANGA)) {
			item = itemCreater.createItem(mangaPath); // Create item and write in document
			System.out.println("> ADD AND CREATEED");

			return item;

		}
		item = (Manga) save.loadDatas(item);
		System.out.println("> ADD AND LOADED");

		return item;
	}

	private Anime loadAnime(String animePath) {
		Anime item = new Anime();

		item.setDestination(animePath); // It is important to define the destination because it will be used to locate
										// the item in the document
		ItemCreater<Anime> itemCreater = new AnimeItemCreate((AnimeCollection) item.getAnimeCollection());
		if (!isCreateItemInDocument(animePath, Category.ANIME)) {
			item = itemCreater.createItem(animePath); // Create item and write in document
			System.out.println("> ADD AND CREATEED");

			return item;

		}
		item = (Anime) save.loadDatas(item);
		System.out.println("> ADD AND LOADED");

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

}
