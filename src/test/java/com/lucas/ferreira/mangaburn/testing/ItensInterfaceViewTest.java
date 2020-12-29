package com.lucas.ferreira.mangaburn.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import com.diffplug.common.base.StackDumper;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class ItensInterfaceViewTest {
	private MainInterfaceView view;
	private ItemsInterfaceView itensView;

	@Before
	public void setUp() {
		view = new MainInterfaceView();
	
		
		MainLoader loader = new MainLoader(new AnimeCollection());
		Collections collections = null;
		try {
			collections = (Collections) loader.loadCollection("D:\\AnimeBurn").get();
			itensView = new ItemsInterfaceView(collections);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void showItensViewInterface() {
		createFoldersFromFileNames(new File("C:\\Users\\lucfe\\Documents\\AnimeList.txt"));
		view.initAndShowGUI();
		itensView.loadMainInterfaceFX(view);

		waitTestOver();

	}

	public void createFoldersFromFileNames(File file) {
		try {

			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (!data.isEmpty() && !data.contains("[edit]")) {

					String line = data.trim().replace("*", "");

					String path = "D:\\ListaDeAnimes\\";
					File newFolder = new File(path + line);
					newFolder.mkdirs();
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// @Test
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
