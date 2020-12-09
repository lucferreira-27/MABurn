package com.lucas.ferreira.maburn.model;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;

public class FolderReaderModel {
	// ->TODO Expection for don't found files. NullPointException
	
	public List<File> findMangaFoldersInMangaCollectionFolder(MangaCollection mangaCollection) {

		String local = mangaCollection.getDestination();

		File mangaCollectionFolder = new File(local);

		// Collect only directorys to avoid problems
		List<File> listMangaFolders = Arrays
				.asList(mangaCollectionFolder.listFiles((file, name) -> new File(file, name).isDirectory()));

		return listMangaFolders;

	}


	public List<File> findChapterFoldersInMangaFolder(MangaDownloaded manga) {
		String local = manga.getDestination();

		File mangaFolder = new File(local);

		// Collect only directorys to avoid problems
		List<File> listChaptersFolders = Arrays
				.asList(mangaFolder.listFiles((file, name) -> new File(file, name).isDirectory()));

		return listChaptersFolders;
	}

	public List<File> findChapterPagesFilesInChapterFolder(ChapterDownloaded chapter) {
		String local = chapter.getDestination();

		File chapterFolder = new File(local);

		// Collect only files to avoid problems
		List<File> listPagesFiles = Arrays
				.asList(chapterFolder.listFiles((file, name) -> new File(file, name).isFile()));
		return listPagesFiles;
	}
	
	public List<File> findAnimeFoldersInAnimeCollectionFolder(AnimeCollection animeCollection) {

		String local = animeCollection.getDestination();

		File animeCollectionFolder = new File(local);

		// Collect only directorys to avoid problems
		List<File> listAnimeFolders = Arrays
				.asList(animeCollectionFolder.listFiles((file, name) -> new File(file, name).isDirectory()));

		return listAnimeFolders;

	}

	public List<File> findEpisodesFilesInAnimeFolder(AnimeDownloaded anime) {
		String local = anime.getDestination();

		File animeFolder = new File(local);

		// Collect only files to avoid problems
		List<File> listEpisodesFiles = Arrays
				.asList(animeFolder.listFiles((file, name) -> new File(file, name).isFile()));
		return listEpisodesFiles;
	}
}
