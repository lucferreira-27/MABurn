package com.lucas.ferreira.maburn.model.loader.folder;

import java.io.File;
import java.util.List;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FolderCollectionLoader {

	private BooleanProperty loadCompleted = new SimpleBooleanProperty(false);
	private FolderCollectionItemLoader itemLoader = new FolderCollectionItemLoader();
	private ObservableList<CollectionTitle> obsItems =FXCollections.observableArrayList();

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

	public ObservableList<CollectionTitle> loadCollectionAsync(String destination, Category category, ObservableList<CollectionTitle> obsItems) {
		this.obsItems = obsItems;
		FolderReaderModel reader = new FolderReaderModel();
		new Thread(() -> {
			if (category == Category.MANGA) {
				loadMangaCollection(reader, destination);
			}
			if (category == Category.ANIME) {
				loadAnimeCollection(reader, destination);
			}
		}).start();;
		return obsItems;

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

			CollectionTitle item = null;

			if (category == Category.ANIME) {
				item = new AnimeDownloaded();
				// item = itemLoader.loadCollectionItems(file.getAbsolutePath(), category);

			}
			if (category == Category.MANGA) {
				item = new MangaDownloaded();
				// item = itemLoader.loadCollectionItems(file.getAbsolutePath(), category);

			}

			item.setDestination(file.getAbsolutePath());
			obsItems.add(item);

		}
		collection.getItens().addAll(obsItems);
		loadCompleted.set(true);
	}

	public BooleanProperty getLoadCompleted() {
		return loadCompleted;
	}

//	@Override
//	public Collections loadCollection(String destination, Category category) {
//		
//		return null;
//	}
//
//	@Override
//	public void loadItem(CollectionItem item) {
//		
//
//	}
//
//	@Override
//	public void setDestination(String destination) {
//		
//
//	}
//
//	@Override
//	protected Collections call() throws Exception {
//		return null;
//	}

}
