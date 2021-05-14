package com.lucas.ferreira.maburn.model.webscraping;

import java.util.Scanner;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.util.Resources;

public class Evaluate {

	private Scanner scanner;

	public String findItemScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/" + site.name() + "/scripts/item.js"));
		String script = "";
		while (scanner.hasNext()) {
			script += scanner.nextLine() + "\n";
		}
		return script;
	}

	public String findTitleScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/" + site.name() + "/scripts/title.js"));
		String script = "";
		while (scanner.hasNext()) {
			script += scanner.nextLine() + "\n";
		}

		return script;
	}

}
