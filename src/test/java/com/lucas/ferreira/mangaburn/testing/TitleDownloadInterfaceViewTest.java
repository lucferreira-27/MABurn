package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleDownloadInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;

public class TitleDownloadInterfaceViewTest {
	private MainInterfaceView view;
	private ItemsInterfaceView itensView;
	private TitleDownloadInterfaceView titleDownloadView;
	private TitleInterfaceView titleView;

	@Before
	public void setUp() {
		view = new MainInterfaceView();
	
		
		MainLoader loader = new MainLoader(new AnimeCollection());
		Collections collections = null;
		try {
			collections = (Collections) loader.loadCollection("D:\\AnimeBurn").get();
			for(CollectionItem item : collections.getItens()) {
				if(item.getTitleDataBase().equals("Munou na Nana")) {
				
					collections.setActualItem(item);

				}
			}
			collections.getActualItem().setCollections(collections);
			itensView = new ItemsInterfaceView(collections);
			titleView = new TitleInterfaceView(itensView);
			titleDownloadView = new TitleDownloadInterfaceView(titleView);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
      
	@Test
	public void showTitleDownloadView() {
		view.setVisibility(true);
		view.initAndShowGUI();
		titleDownloadView.loadMainInterfaceFX(view);

		waitTestOver();

	}
	private void waitTestOver() {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
}
