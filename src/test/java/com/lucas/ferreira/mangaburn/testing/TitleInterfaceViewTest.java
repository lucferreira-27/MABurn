package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.view.ItensInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;

public class TitleInterfaceViewTest {
	private MainInterfaceView view;
	private TitleInterfaceView titleView;

	@Before
	public void setUp() {
		view = new MainInterfaceView();
		titleView = new TitleInterfaceView(new ItensInterfaceView(new AnimeCollection()));
	}

	@Test
	public void showTitle() {
		view.initAndShowGUI();
		titleView.loadMainInterfaceFX(view);
		waitTestOver();

	}

	private void waitTestOver() {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}

}
