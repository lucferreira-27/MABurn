//package com.lucas.ferreira.mangaburn.testing;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThat;
//
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import com.lucas.ferreira.maburn.controller.CommandLineController;
//import com.lucas.ferreira.maburn.model.FolderReaderModel;
//import com.lucas.ferreira.maburn.model.collections.MangaCollection;
//import com.lucas.ferreira.maburn.model.loader.SlowMangaCollectionLoader;
//import com.lucas.ferreira.maburn.view.CommandLineView;
//
//public class CollectionControllerTest {
//	private static final String MANGA_LOCAL = "D:\\MABurnTest\\Mangas";
//	private static final String ANIME_LOCAL = "D:\\MABurnTest\\Animes";
//	private CommandLineController collectionController;
//	private FolderReaderModel reader;
//	private MangaCollection mangaCollection;
//	private SlowMangaCollectionLoader slowLoader;
//	private CommandLineView view;
//	@Before
//	public void setUp() {
//		view = new CommandLineView();
//		slowLoader = new SlowMangaCollectionLoader();
//		mangaCollection = new MangaCollection();
//		collectionController = new CommandLineController(view, slowLoader, mangaCollection);
//		reader = new FolderReaderModel();
//	}
//	@Test
//	public void run() {
//		collectionController.run();
//
//	}
//	//@Test
//	public void sessionSelectCollectionTest() {
//		collectionController.sessionSelectCollection();
//	}
//	//@Test
//	public void sessionSelectItemTest() {
//		collectionController.sessionSelectItem();
//	}
//	//@Test
//	public void sessionMenuTest() {
//		collectionController.sessionMenu();
//	}
//	
//
//	
//
//}
