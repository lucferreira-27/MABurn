package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.controller.HomeInterfaceController;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class HomeInterfaceControllerTest {
	private HomeInterfaceController homeController;
	private HomeInterfaceView homeView;
	private ItemsInterfaceView itensView;

	@Before
	public void setUp() {
		homeView = new HomeInterfaceView();
		AnimeCollection animeCollection = new AnimeCollection();
		AnimeDownloaded anime = new AnimeDownloaded();
		animeCollection.getListAnimes().add(anime);
		anime.setImageUrl("https://cdn.myanimelist.net/images/manga/2/223694.jpg");
		Collections collection = animeCollection;

		itensView = new ItemsInterfaceView(collection);

	}

	@Test
	public void testController() {
		MainInterfaceView.getInstance().initAndShowGUI();
		homeView.loadMainInterfaceFX();
		homeController = new HomeInterfaceController();
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	@Test
	public void onClickButtonHomeTest() {
		
	}
}
