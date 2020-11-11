//package com.lucas.ferreira.mangaburn.testing;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertThat;
//
//import java.io.File;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.lucas.ferreira.maburn.model.FolderReaderModel;
//import com.lucas.ferreira.maburn.model.bean.Chapter;
//import com.lucas.ferreira.maburn.model.bean.Manga;
//import com.lucas.ferreira.maburn.model.collections.MangaCollection;
//import com.lucas.ferreira.maburn.model.enums.Category;
//import com.lucas.ferreira.maburn.model.loader.SlowMangaCollectionLoader;
//
//public class SlowMangaCollectionLoaderTest {
//	private SlowMangaCollectionLoader slowLoader;
//	private FolderReaderModel reader;
//	private MangaCollection mangaCollection;
//
//	@Before
//	public void setUp() {
//		slowLoader = new SlowMangaCollectionLoader();
//		reader = new FolderReaderModel();
//	}
//
//	@Test
//	public void loadMangaCollectionTest() {
//		MangaCollection mangaCollection = (MangaCollection) slowLoader.loadCollection("D:\\MangaBurnTest",Category.MANGA);
//		int expect = mangaCollection.getListMangas().size();
//		assertThat(expect, is(equalTo(6)));
//	}
//
//	@Test
//	public void loadChapterTest() {
//		MangaCollection mangaCollection = (MangaCollection) slowLoader.loadCollection("D:\\MangaBurnTest",Category.ANIME);
//		Manga manga = mangaCollection.getListMangas().get(4);
//		List<File> chaptersFolders = reader.findChapterFoldersInMangaFolder(manga);
//
//		slowLoader.addAllChaptersInManga(manga, chaptersFolders);
//
//		int expect = manga.getListChapters().size();
//
//		assertThat(expect, is(equalTo(8)));
//	}
//
//	@Test
//	public void loadPagesTest() {
//		MangaCollection mangaCollection = (MangaCollection) slowLoader.loadCollection("D:\\MangaBurnTest",Category.ANIME);
//		Manga manga = mangaCollection.getListMangas().get(4);
//
//		List<File> chaptersFolders = reader.findChapterFoldersInMangaFolder(manga);
//
//		slowLoader.addAllChaptersInManga(manga, chaptersFolders);
//
//		Chapter chapter = manga.getListChapters().get(1);
//
//		slowLoader.addAllPagesInChapter(chapter, reader.findChapterPagesFilesInChapterFolder(chapter));
//
//		int expect = manga.getListChapters().get(1).getPagesImages().size();
//
//		assertThat(expect, is(equalTo(15)));
//	}
//	
//	@Test
//	public void loadAllItemsTest() {
//		MangaCollection mangaCollection = (MangaCollection) slowLoader.loadCollection("D:\\MangaBurnTest",Category.MANGA);
//		Manga manga = mangaCollection.getListMangas().get(4);
//		int unexpect = manga.getListChapters().size();
//		slowLoader.loadAllItems();
//		assertNotEquals(unexpect, manga.getListChapters().size());
//	}
//	@Test
//	public void loadAllSubItemsTest() {
//		MangaCollection mangaCollection = (MangaCollection) slowLoader.loadCollection("D:\\MangaBurnTest",Category.MANGA);
//		Manga manga = mangaCollection.getListMangas().get(4);
//		slowLoader.loadAllItems();
//		Chapter chapter = manga.getListChapters().get(1);
//		
//		int unexpect = chapter.getPagesImages().size();
//		slowLoader.loadAllSubItems();
//		assertNotEquals(unexpect, chapter.getPagesImages().size());
//	}
//}
