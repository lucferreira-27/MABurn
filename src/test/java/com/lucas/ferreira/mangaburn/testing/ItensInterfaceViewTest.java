package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItensInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class ItensInterfaceViewTest {
	private MainInterfaceView view;
	private ItensInterfaceView itensView;
	@Before
	public void setUp() {
		view = new MainInterfaceView();
		
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setImageUrl("https://cdn.myanimelist.net/images/anime/1171/109222.jpg");
		AnimeCollection animeCollection =new AnimeCollection();
		for(int i = 0; i < 100; i++) {
			animeCollection.getListAnimes().add(anime);

		}


		Collections collection = animeCollection;
	
		itensView = new ItensInterfaceView(collection);
	}
	

	@Test
	public void showItensViewInterface() {
		view.initAndShowGUI();
		itensView.loadMainInterfaceFX(view);
		
		 waitTestOver();

	}
	//@Test 
	public void homeInterfaceToItensInterface() {
		 HomeInterfaceView homeView = new HomeInterfaceView();
		 view.initAndShowGUI();
		 homeView.loadMainInterfaceFX(view);
		 itensView.loadMainInterfaceFX(view); 
		 waitTestOver();
		 
	}
	
	
	private void waitTestOver() {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	

}
