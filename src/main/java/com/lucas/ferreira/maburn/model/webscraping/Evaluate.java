package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.util.Resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Evaluate {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Scanner scanner;
	
	
	public String findMainScript() {
		LOGGER.config("Find main script");
		scanner = new Scanner(Resources.getResourceAsStream("scraping/main.js"));
		String script = read();
		LOGGER.config("Main script found");
		return script;
	}

	public String findScript(RegisteredSite registeredSite) throws FileNotFoundException {

		scanner = new Scanner(new FileInputStream(registeredSite.getFolder().toString() + "\\scripts\\" + registeredSite.getSiteConfig().getScriptPath()));
		return read();
	}
	public String findScript(String name, String script) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + name + "/scripts/" + script));
		return read();
	}

	
	
	public String findItemScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + site.name() + "/scripts/item.js"));
		return read();
	}

	public String findTitleScript(Sites site) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + site.name() + "/scripts/title.js"));

		return read();
	}
	public String findSearchScript(SearchEngine engine) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/search/" + engine.name() + "/scripts/results.js"));

		return read();
	}

	private String read() {
		StringBuilder script = new StringBuilder();
		while (scanner.hasNext()) {
			script.append(scanner.nextLine()).append("\n");
		}
		return script.toString();
	}

}
