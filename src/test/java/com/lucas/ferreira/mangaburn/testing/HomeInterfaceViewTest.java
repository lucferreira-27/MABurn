package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class HomeInterfaceViewTest {
	private MainInterfaceView view;
	private HomeInterfaceView homeView;
	@Before
	public void setUp() {
		view = new MainInterfaceView();
		homeView = new HomeInterfaceView();
		
	}
	

	@Test
	public void showHomeViewInterface() {
		view.initAndShowGUI();
		
		homeView.loadMainInterfaceFX(view);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
}
