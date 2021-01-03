package com.lucas.ferreira.maburn.model.search;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.sites.BingScraping;

public class BingSearch {
	private static final String BING_SEARCH = "https://www.bing.com/search?q=";
	private static final String FILTER = " site: ";
	private static final String FORM = "&form=QBTE";
	private BingScraping bingScraping;
	private Document doc;
	private Sites site;
	private String title;

	public BingSearch(String title, Sites site) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.site = site;
	}

	public String search() throws WebScrapingException, ConnectionException {
		String querry;
		if (site != null) {
			querry = BING_SEARCH + title + FILTER + site.getUrl()+ " " + FORM;
		} else {
			querry = BING_SEARCH + title + " " + FORM;

		}
		System.out.println("querry: "  +  querry);
		doc = Jsoup.parse(ConnectionModel.connect(querry, 3));
		bingScraping = new BingScraping(doc, site);
		return bingScraping.getFirstMathResult();

	}
	public List<String> searchAll() throws WebScrapingException, ConnectionException {
		String querry;
		if (site != null) {
			querry = BING_SEARCH + title + FILTER + site.getUrl() + FORM;
		} else {
			querry = BING_SEARCH + title + " " + FORM;

		}
		doc = Jsoup.parse(ConnectionModel.connect(querry, 3));
		bingScraping = new BingScraping(doc, site);
		return bingScraping.getAllResult();

	}
	

}
