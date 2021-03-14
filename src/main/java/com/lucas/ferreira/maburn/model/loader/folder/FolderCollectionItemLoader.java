package com.lucas.ferreira.maburn.model.loader.folder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FolderCollectionItemLoader {
	
	private BooleanProperty loadCompleted = new SimpleBooleanProperty(false);

	public CollectionItem loadCollectionItems(String destination, Category category) {
		FolderReaderModel reader = new FolderReaderModel();

		if (category == Category.MANGA) {
			return loadMangaDownloaded(reader,destination);
		}
		if (category == Category.ANIME) {
			return loadAnimeDownloaded(reader,destination);
		}

		return null;
	}

	private CollectionItem loadMangaDownloaded(FolderReaderModel reader, String destination) {
		CollectionItem item;
		List<File> files;

		item = new MangaDownloaded(destination);
		files = reader.findChapterFoldersInMangaFolder((MangaDownloaded) item);

		addSubItemInItem(item, files, Category.MANGA);
		return item;

	}



	private CollectionItem loadAnimeDownloaded(FolderReaderModel reader, String destination) {
		CollectionItem item;
		List<File> files;
		item = new AnimeDownloaded(destination);
		files = reader.findEpisodesFilesInAnimeFolder((AnimeDownloaded) item);
		addSubItemInItem(item, files, Category.ANIME);
		return item;

	}
	private void addSubItemInItem(CollectionItem item, List<File> files, Category manga) {
		List<CollectionSubItem> subItems = new ArrayList<>();
		for (File file : files) {
			CollectionSubItem subItem = null;
			if (manga == Category.MANGA)
				subItem = new ChapterDownloaded();
			if (manga == Category.ANIME)
				subItem = new EpisodeDownloaded();
			subItem.setDestination(file.getAbsolutePath());
			subItem.setName(file.getName());
			subItems.add(subItem);

		}
		item.addSubItens(subItems);

		loadCompleted.set(true);
	}
}
