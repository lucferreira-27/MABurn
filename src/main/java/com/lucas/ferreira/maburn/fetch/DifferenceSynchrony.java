package com.lucas.ferreira.maburn.fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.predicates.ItemWebDataFilter;

public class DifferenceSynchrony {
	private FolderReaderModel reader = new FolderReaderModel();
	private List<ItemWebData> items = new ArrayList<ItemWebData>();

	public DifferenceSynchrony(List<ItemWebData> items) {
		// TODO Auto-generated constructor stub
		this.items = items;
	}

	public List<ItemWebData> mustRecentOnly(CollectionItem title) {
		//List<ItemWebData> list = synch(title);



		return null;
	}

	public List<ItemWebData> oldOnly() {
		return null;
	}

	public List<ItemWebData> synch(CollectionItem title) {
		List<String> avaibleNameItems = new ArrayList<>();

		switch (title.getCategory()) {
		case ANIME:
			reader.findEpisodesFilesInAnimeFolder((AnimeDownloaded) title).forEach(file -> {
				String name = file.getName().substring(0, file.getName().lastIndexOf("."));
				avaibleNameItems.add(name);
			});
			break;
		case MANGA:
			reader.findChapterFoldersInMangaFolder((MangaDownloaded) title).forEach(file -> {
				String name = file.getName();
				System.out.println(name);

				avaibleNameItems.add(name);
			});

			break;
		default:
			break;
		}

		List<ItemWebData> itemsToUpdate = new ArrayList<>();
		itemsToUpdate = items.stream().filter(new ItemWebDataFilter(avaibleNameItems)).collect(Collectors.toList());
		return itemsToUpdate;
	}

}
