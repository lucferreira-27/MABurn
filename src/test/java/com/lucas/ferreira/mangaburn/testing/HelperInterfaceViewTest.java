package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class HelperInterfaceViewTest {
	private MainInterfaceView view;
	private HelperInterfaceView homeView;
	@Before
	public void setUp() {
		view = new MainInterfaceView();
		homeView = new HelperInterfaceView();
		
	}
	

	@Test
	public void showHomeViewInterface() {
		view.initAndShowGUI();
		
		homeView.loadMainInterfaceFX();
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
}
