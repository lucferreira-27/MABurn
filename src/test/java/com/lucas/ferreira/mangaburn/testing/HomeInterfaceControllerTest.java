package com.lucas.ferreira.mangaburn.testing;

import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.controller.HomeInterfaceController;
import com.lucas.ferreira.maburn.controller.MenuController;
import com.lucas.ferreira.maburn.model.bean.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItensInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class HomeInterfaceControllerTest {
	private HomeInterfaceController homeController;
	private MainInterfaceView mainView;
	private HomeInterfaceView homeView;
	private ItensInterfaceView itensView;

	@Before
	public void setUp() {
		mainView = new MainInterfaceView();
		homeView = new HomeInterfaceView();
		AnimeCollection animeCollection = new AnimeCollection();
		AnimeDownloaded anime = new AnimeDownloaded();
		animeCollection.getListAnimes().add(anime);
		anime.setImageUrl("https://cdn.myanimelist.net/images/manga/2/223694.jpg");
		Collections collection = animeCollection;

		itensView = new ItensInterfaceView(collection);

	}

	@Test
	public void testController() {
		mainView.initAndShowGUI();
		homeView.loadMainInterfaceFX(mainView);
		homeController = new HomeInterfaceController(mainView);
		assertTrue(homeController.active());
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	@Test
	public void onClickButtonHomeTest() {
		
	}
}
