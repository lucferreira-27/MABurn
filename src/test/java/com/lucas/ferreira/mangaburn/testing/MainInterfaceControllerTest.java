//package com.lucas.ferreira.mangaburn.testing;
//
//import java.util.Scanner;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.lucas.ferreira.maburn.controller.MainInterfaceController;
//import com.lucas.ferreira.maburn.model.FolderReaderModel;
//import com.lucas.ferreira.maburn.model.collections.MangaCollection;
//import com.lucas.ferreira.maburn.model.loader.SlowMangaCollectionLoader;
//import com.lucas.ferreira.maburn.view.MainInterfaceView;
//
//public class MainInterfaceControllerTest {
//	private MainInterfaceController controller;
//	private FolderReaderModel reader;
//	private MangaCollection mangaCollection;
//	private SlowMangaCollectionLoader slowLoader;
//	private MainInterfaceView view;
//	@Before
//	public void setUp() {
//		view = new MainInterfaceView();
//		slowLoader = new SlowMangaCollectionLoader();
//		mangaCollection = new MangaCollection();
//		reader = new FolderReaderModel();
//		controller = new MainInterfaceController(view, mangaCollection);
//	}
//	
//	@Test
//	public void runTest() {
//		controller.run();
//		Scanner scan = new Scanner(System.in);;
//		scan.nextLine();
//	}
//	
//}
