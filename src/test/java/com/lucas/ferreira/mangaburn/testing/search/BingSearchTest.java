package com.lucas.ferreira.mangaburn.testing.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.BingSearch;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class BingSearchTest {
	private final static String TITLE = "Shingeki no Kyojin The Final Season";
	private List<String> names = new ArrayList<String>();

	public void getNamesFromFile(File file) {
		try {

			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (!data.isEmpty() && !data.contains("[edit]")) {

					String line = data.trim().replace("*", "");

					names.add(line);
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			CustomLogger.log("An error occurred.");
			e.printStackTrace();
		}
	}

	@Before
	public void setup() {
		if (names.isEmpty())
			getNamesFromFile(new File("C:\\Users\\lucfe\\Documents\\Seasson Fall 2020.txt"));
	}

	@Test
	public void searchGoyabuStressTest() {
		int fails = 0;
		for(int i = 0; i < 100; i++) {
			BingSearch bingSearch = new BingSearch(names.get(i), Sites.GOYABU);
			try {
			CustomLogger.log(bingSearch.search());
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println("Fail: " + names.get(i));
				fails++;
			}
		}
		CustomLogger.log("Fails: " + fails);
//		BingSearch bingSearch = new BingSearch(TITLE, Sites.GOYABU);
//		CustomLogger.log(bingSearch.search());
	}

	@Test
	public void searchAnitubeStressTest() {
		int fails = 0;
		for(int i = 0; i < 100; i++) {
			BingSearch bingSearch = new BingSearch(names.get(i), Sites.ANITUBE);
			try {
			CustomLogger.log(bingSearch.search());
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println("Fail: " + names.get(i));
				fails++;
			}
		}
		CustomLogger.log("Fails: " + fails);
	}

	@Test
	public void searchSaikoStressTest() {
		int fails = 0;
		for(int i = 0; i < 100; i++) {
			BingSearch bingSearch = new BingSearch(names.get(i), Sites.SAIKO);
			try {
			CustomLogger.log(bingSearch.search());
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println("Fail: " + names.get(i));
				fails++;
			}
		}
		CustomLogger.log("Fails: " + fails);
	}

	@Test
	public void searchMangaHostStressTest() {
		BingSearch bingSearch = new BingSearch(TITLE, Sites.MANGA_HOST);
		CustomLogger.log(bingSearch.search());
	}

	@Test
	public void searchMangaYabuStressTest() {
		BingSearch bingSearch = new BingSearch(TITLE, Sites.MANGA_YABU);
		CustomLogger.log(bingSearch.search());
	}

}
