package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;
import com.lucas.ferreira.maburn.view.TitleSearchInterfaceView;

public class TitleInterfaceViewTest {
	private MainInterfaceView view;
	private TitleSearchInterfaceView titleView;

	@Before
	public void setUp() {
		view = new MainInterfaceView();
		titleView = new TitleSearchInterfaceView(new ItemsInterfaceView(new AnimeCollection()));
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
