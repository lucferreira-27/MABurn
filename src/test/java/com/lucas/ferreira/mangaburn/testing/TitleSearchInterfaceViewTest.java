package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleSearchInterfaceView;

public class TitleSearchInterfaceViewTest {
	private MainInterfaceView view;
	private TitleSearchInterfaceView titleView;

	@Before
	public void setUp() {
		view = new MainInterfaceView();
		AnimeCollection animeCollection = new AnimeCollection();
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setImageLocal("C:\\Users\\lucfe\\Documents\\MangaBurn\\Files\\Thumbnails\\Animes\\Ahiru no Sora.jpg");
		animeCollection.setActualItem(anime);
		titleView = new TitleSearchInterfaceView(new ItemsInterfaceView(animeCollection));
	}

	@Test
	public void showTitle() {
		view.initAndShowGUI();
		titleView.loadMainInterfaceFX();
		waitTestOver();

	}

	private void waitTestOver() {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}

}
