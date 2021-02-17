package com.lucas.ferreira.maburn.model.search;

import java.util.List;

import org.jsoup.nodes.Document;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoogleScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.web.WebView;

public class GoogleSearch implements SearchEngine {
	private WebView googleBrowser;
	private static final String GOOGLE_SEARCH = "https://www.google.com/search?q=";
	private static final String FILTER = " site: ";
	private GoogleScraping googleScraping;
	private Document doc;
	private Sites site;
	private String title;

	public GoogleSearch(String title, Sites site) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.site = site;
		Platform.runLater(() -> {
			googleBrowser = new WebView();
		});
		while (googleBrowser == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String search() throws WebScrapingException, ConnectionException {

		String querry;

		if (site != null) {
			querry = GOOGLE_SEARCH + title + FILTER + site.getUrl();
		} else {
			querry = GOOGLE_SEARCH + title;

		}
		CustomLogger.log("querry: " + querry);

		Platform.runLater(() -> {

			googleBrowser.getEngine().load(querry);

		});
		googleScraping = new GoogleScraping(googleBrowser.getEngine());
		String result = googleScraping.getFirstMathResult();
		if (result.contains(site.getUrl()))
			return result;
		else
			throw new WebScrapingException("No results");

	}

	public String searchNoFilter() throws WebScrapingException, ConnectionException {
		String querry;

		querry = GOOGLE_SEARCH + title + " " + site.getUrl();

		CustomLogger.log("querry: " + querry);

		googleBrowser.getEngine().load(querry);

		googleScraping = new GoogleScraping(googleBrowser.getEngine());
		return googleScraping.getFirstMathResult();

	}

	public List<String> searchAll() throws WebScrapingException, ConnectionException {
		String querry;
		if (site != null) {
			querry = GOOGLE_SEARCH + title + FILTER + site.getUrl();
		} else {
			querry = GOOGLE_SEARCH + title;

		}
		googleBrowser.getEngine().load(querry);

		googleScraping = new GoogleScraping(googleBrowser.getEngine());
		return googleScraping.getAllResult();

	}

}
