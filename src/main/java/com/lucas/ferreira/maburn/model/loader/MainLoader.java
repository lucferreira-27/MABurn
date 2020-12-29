package com.lucas.ferreira.maburn.model.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class MainLoader {
	private Collections collection;
	private CollectionLoader loader;

	public MainLoader(CollectionLoader loader) {
		// TODO Auto-generated constructor stub
		this.loader = loader;

	}



	public MainLoader(Collections collection) {
		// TODO Auto-generated constructor stub
		this.collection = collection;
		this.loader = new SlowCollectionLoader(collection);

	}
	public MainLoader(Category category) {
		// TODO Auto-generated constructor stub
		this.loader = new SlowCollectionLoader(collection);

	}

	public CollectionLoader loadCollection(String destination) {
		
		final ExecutorService exec = Executors.newFixedThreadPool(5, r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});

		loader.setDestination(destination);
		exec.submit(loader);
		
		
		exec.shutdown();

		return loader;
	}

	// Loads select Manga, used index to selection
	public CollectionItem loadSelectItem(int index) {

		if (collection instanceof MangaCollection) {
			return loadSelectManga(index);
		} else if (collection instanceof AnimeCollection) {
			return loadSelectAnime(index);
		}
		return null;

	}

	public Collections selectCollection(int index) {
		if (index == 0)
			return new AnimeCollection();
		else if (index == 1)
			return new MangaCollection();
		return null;
	}

	public CollectionItem loadSelectCollection(int index) {

		if (collection instanceof MangaCollection) {
			return loadSelectManga(index);
		} else if (collection instanceof AnimeCollection) {
			return loadSelectAnime(index);
		}
		return null;

	}

	private MangaDownloaded loadSelectManga(int id) {

		MangaDownloaded manga = (MangaDownloaded) collection.getItens().stream().filter(item -> item.getId() == id).findFirst().get();

		setActualItemInCollection(manga);

		loadAllSubItemInItem(manga);
		return manga;
	}

	private AnimeDownloaded loadSelectAnime(int id) {

		AnimeDownloaded anime = (AnimeDownloaded) collection.getItens().stream().filter(item -> item.getId() == id).findFirst().get();

		setActualItemInCollection(anime);

		loadAllSubItemInItem(anime);
		return anime;
	}

	public void loadAllSubItemInItem(CollectionItem item) {
		loader.loadItem(item);
	}

	public void setActualItemInCollection(CollectionItem item) {
		collection.setActualItem(item);
	}

}
