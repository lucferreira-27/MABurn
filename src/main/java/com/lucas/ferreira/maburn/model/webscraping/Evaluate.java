package com.lucas.ferreira.maburn.model.webscraping;

import java.util.Scanner;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.util.Resources;

public class Evaluate {

	private Scanner scanner;

	public String findItemScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + site.name() + "/scripts/item.js"));
		String script = read();
		return script;
	}

	public String findTitleScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + site.name() + "/scripts/title.js"));
		String script = read();

		return script;
	}
	public String findSearchScript(SearchEngine engine) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/search/" + engine.name() + "/scripts/results.js"));
		String script = read();

		return script;
	}

	private String read() {
		String script = "";
		while (scanner.hasNext()) {
			script += scanner.nextLine() + "\n";
		}
		return script;
	}

}
