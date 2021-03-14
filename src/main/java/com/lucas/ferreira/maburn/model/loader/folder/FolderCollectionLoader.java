package com.lucas.ferreira.maburn.model.loader.folder;

import java.io.File;
import java.util.List;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FolderCollectionLoader {

	private BooleanProperty loadCompleted = new SimpleBooleanProperty(false);
	public Collections loadCollection(String destination, Category category) {
		FolderReaderModel reader = new FolderReaderModel();
		loadCompleted.set(false);
		if (category == Category.MANGA) {
			return loadMangaCollection(reader, destination);
		}
		if (category == Category.ANIME) {
			return loadAnimeCollection(reader, destination);
		}
		return null;
	}

	private Collections loadMangaCollection(FolderReaderModel reader, String destination) {
		Collections collection;
		List<File> files;

		collection = new MangaCollection(destination);
		files = reader.findMangaFoldersInMangaCollectionFolder((MangaCollection) collection);
		
		addItemInCollection(collection, files, Category.MANGA);

		return collection;

	}

	private Collections loadAnimeCollection(FolderReaderModel reader, String destination) {
		Collections collection;
		List<File> files;

		collection = new AnimeCollection(destination);
		files = reader.findAnimeFoldersInAnimeCollectionFolder((AnimeCollection) collection);

		addItemInCollection(collection, files, Category.ANIME);
		return collection;

	}

	private void addItemInCollection(Collections collection, List<File> files, Category category) {
		for (File file : files) {

			CollectionItem item = null;
			
			if (category == Category.ANIME)
				item = new AnimeDownloaded();
			if (category == Category.MANGA) {
				item = new MangaDownloaded();
			}
			
			item.setDestination(file.getAbsolutePath());
			
			collection.getItens().add(item);
		}
		loadCompleted.set(true);
	}

	public BooleanProperty getLoadCompleted() {
		return loadCompleted;
	}

//	@Override
//	public Collections loadCollection(String destination, Category category) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void loadItem(CollectionItem item) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDestination(String destination) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected Collections call() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
