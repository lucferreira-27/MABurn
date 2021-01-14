package com.lucas.ferreira.maburn.model.update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.predicates.ItemWebDataFilter;

public class ItemUpdate {

	private CollectionItem itemCollection;
	private List<ItemWebData> items;
	private int toUpdate = 0;

	public ItemUpdate(List<ItemWebData> items, CollectionItem itemCollectionItem) {
		this.items = items;
		this.itemCollection = itemCollectionItem;

	}

	/*
	 * Update item - First synchronize - Second fetch selected items
	 */
	public List<ItemWebData> update() {

		// Create a future list.

		// data receive only items who are not downloaded.
		List<ItemWebData> datas = synchronizeItem();

		return datas;
	}

	public List<ItemWebData> synchronizeItem() {
		List<String> avaibleNameItems = new ArrayList<>();
		FolderReaderModel folderReaderModel = new FolderReaderModel();
		try {

			if (itemCollection.getCategory() == Category.ANIME) {
				folderReaderModel.findEpisodesFilesInAnimeFolder((AnimeDownloaded) itemCollection).forEach(file -> {
					String name = file.getName().substring(0, file.getName().lastIndexOf("."));
					avaibleNameItems.add(name);
				});

			} else if (itemCollection.getCategory() == Category.MANGA) {
				folderReaderModel.findChapterFoldersInMangaFolder((MangaDownloaded) itemCollection).forEach(file -> {

					String name = file.getName();
					avaibleNameItems.add(name);

				});

			}

			List<ItemWebData> itemsToUpdate = new ArrayList<>();
			// ItemWebDataFilter filter items don't found in folder
			itemsToUpdate = items.stream().filter(new ItemWebDataFilter(avaibleNameItems)).collect(Collectors.toList());

			this.toUpdate = itemsToUpdate.size();
			return itemsToUpdate;
		} catch (WebScrapingException e) {
			// TODO: handle exception
			e.printStackTrace();
			CustomLogger.log("return " + 0);
			return null;
		}

	}

	public CollectionItem getItem() {
		return itemCollection;
	}

	public int getToUpdate() {
		return toUpdate;
	}
}
