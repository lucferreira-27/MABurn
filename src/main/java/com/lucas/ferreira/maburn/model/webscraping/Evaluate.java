package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.ResourcesFile;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
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
		scanner = new Scanner(ResourcesFile.pathToInputStream(Paths.get(registeredSite.getFolder().toString() + "\\scripts\\" + registeredSite.getSiteConfig().getScriptPath())));
		String findScript = read();
		return findScript;
	}
	public String findScript(String name, String script) {

		scanner = new Scanner(Resources.getResourceAsStream("scraping/sites/" + name + "/scripts/" + script));
		String findScript = read();
		return findScript;
	}

	
	
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
