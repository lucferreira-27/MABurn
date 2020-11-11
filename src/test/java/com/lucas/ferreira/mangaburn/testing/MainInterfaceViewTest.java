package com.lucas.ferreira.mangaburn.testing;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class MainInterfaceViewTest {
	private MainInterfaceView view;
	
	@Before
	public void setUp() {
		view = new MainInterfaceView();
	}
	
	@Test
	public void showMenuTest() {
		view.initAndShowGUI();
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
}
