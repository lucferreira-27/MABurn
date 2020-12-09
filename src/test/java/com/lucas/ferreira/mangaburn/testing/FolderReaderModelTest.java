package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;

public class FolderReaderModelTest {
	private static final String MANGA_LOCAL = "D:\\MABurnTest\\Mangas";
	private static final String ANIME_LOCAL = "D:\\MABurnTest\\Animes";
	private FolderReaderModel folderReaderModel;
	private MangaCollection mangaCollection;
	private AnimeCollection animeCollection;

	@Before
	public void setUp() {
		folderReaderModel = new FolderReaderModel();
		mangaCollection = new MangaCollection();
		animeCollection = new AnimeCollection();
		animeCollection.setDestination(ANIME_LOCAL);
		mangaCollection.setDestination(MANGA_LOCAL);
	}

	@Test
	public void findMangaFoldersInMangaCollectionFolder() {
		String local = mangaCollection.getDestination();
		List<File> listMangasFolders = folderReaderModel.findMangaFoldersInMangaCollectionFolder(mangaCollection);

		List<File> listExcpeted = Arrays.asList(new File(MANGA_LOCAL + "\\Dragon Ball"),
				new File(MANGA_LOCAL + "\\Golden Kamuy"), new File(MANGA_LOCAL + "\\Kimetsu no Yaiba"),
				new File(MANGA_LOCAL + "\\Naruto"), new File(MANGA_LOCAL + "\\Solo Leveling"),
				new File(MANGA_LOCAL + "\\Tower of God"));
		assertThat(listMangasFolders.size(), is(listExcpeted.size()));

	}

	@Test
	public void findChapterFoldersInMangaFolder() {
		MangaDownloaded manga = new MangaDownloaded();
		manga.setDestination(MANGA_LOCAL + "\\Solo Leveling");
		String local = manga.getDestination();
		List<File> listChaptersFolders = folderReaderModel.findChapterFoldersInMangaFolder(manga);

		assertThat(listChaptersFolders.size(), is(equalTo(8)));
	}

	@Test
	public void findChapterPagesFilesInChapterFolder() {
		ChapterDownloaded chapter = new ChapterDownloaded();
		chapter.setDestination(MANGA_LOCAL + "\\Solo Leveling\\Capítulo 1");
		String local = chapter.getDestination();
		List<File> listPagesFiles = folderReaderModel.findChapterPagesFilesInChapterFolder(chapter);

		assertThat(listPagesFiles.size(), is(equalTo(15)));
	}

	@Test
	public void findAnimeFoldersInAnimeCollectionFolder() {
		String local = mangaCollection.getDestination();
		List<File> listAnimeFolders = folderReaderModel.findAnimeFoldersInAnimeCollectionFolder(animeCollection);

		List<File> listExcpeted = Arrays.asList(new File(ANIME_LOCAL + "\\Enen no Shouboutai  Ni no Shou"),
				new File(ANIME_LOCAL + "\\Shokugeki no Souma  Gou no Sara"),
				new File(ANIME_LOCAL + "\\The God of High School"));

		assertThat(listAnimeFolders.size(), is(listExcpeted.size()));
	}
	@Test
	public void findEpisodesFilesInAnimeFolder() {
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setDestination(ANIME_LOCAL + "\\Enen no Shouboutai  Ni no Shou");
		String local = anime.getDestination();
		List<File> listEpisodesFiles = folderReaderModel.findEpisodesFilesInAnimeFolder(anime);

		assertThat(listEpisodesFiles.size(), is(equalTo(12)));
	}

}
