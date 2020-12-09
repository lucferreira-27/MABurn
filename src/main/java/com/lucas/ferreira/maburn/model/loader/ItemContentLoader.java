package com.lucas.ferreira.maburn.model.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
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
		if (item instanceof AnimeDownloaded) {
			readerAnimeSubItem((AnimeDownloaded) item);
		}
		if (item instanceof MangaDownloaded) {
			readerMangaSubItem((MangaDownloaded) item);
		}
	}

	private void readerAnimeSubItem(AnimeDownloaded anime) {
		List<File> files = reader.findEpisodesFilesInAnimeFolder(anime);
		for (File file : files) {
			EpisodeDownloaded episode = new EpisodeDownloaded();
			episode.setAnime(anime);
			episode.setName(file.getName());
			episode.setDestination(file.getAbsolutePath());
			subItens.add(episode);
		}

	}

	private void readerMangaSubItem(MangaDownloaded manga) {
		List<File> files = reader.findChapterFoldersInMangaFolder(manga);
		for (File file : files) {
			ChapterDownloaded chapter = new ChapterDownloaded();
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
