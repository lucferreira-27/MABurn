package com.lucas.ferreira.maburn.model.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Chapter;
import com.lucas.ferreira.maburn.model.bean.Episode;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ItemContentLoader implements Callable<List<CollectionSubItem>> {
	private FolderReaderModel reader = new FolderReaderModel();
	private List<CollectionSubItem> subItens = new ArrayList<>();

	private CollectionItem item;

	@Override
	public List<CollectionSubItem> call() throws Exception {
		// TODO Auto-generated method stub
		// CollectionItem item = loadItem(itemPath, collection);
		getSubItensInFolder();
		return subItens;
	}

	public ItemContentLoader(CollectionItem item) {
		// TODO Auto-generated constructor stub
		this.item = item;
	}

	public void getSubItensInFolder() {
		if (item instanceof Anime) {
			readerAnimeSubItem((Anime) item);
		}
		if (item instanceof Manga) {
			readerMangaSubItem((Manga) item);
		}
	}

	private void readerAnimeSubItem(Anime anime) {
		List<File> files = reader.findEpisodesFilesInAnimeFolder(anime);
		for (File file : files) {
			Episode episode = new Episode();
			episode.setAnime(anime);
			episode.setName(file.getName());
			episode.setDestination(file.getAbsolutePath());
			subItens.add(episode);
		}

	}

	private void readerMangaSubItem(Manga manga) {
		List<File> files = reader.findChapterFoldersInMangaFolder(manga);
		for (File file : files) {
			Chapter chapter = new Chapter();
			chapter.setManga(manga);
			chapter.setName(file.getName());
			chapter.setDestination(file.getAbsolutePath());
			subItens.add(chapter);

		}
	}
	
	public List<CollectionSubItem> getSubItens() {
		 getSubItensInFolder();
		return subItens;
	}

}
