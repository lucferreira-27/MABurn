package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;

public class BingScraping {

	private Document doc;
	private Sites site;
	private Scraper scraper = new Scraper();
	private List<String> links = new ArrayList<String>();

	public BingScraping(Document doc, Sites site) {
		// TODO Auto-generated constructor stub
		this.doc = doc;
		this.site = site;

	}

	private void scraping() {

		Element allResults = scraper.scrapeSnippet(doc, "#b_results").get(0);

		allResults.select(".b_algo").forEach(algo -> {
			links.add(algo.select("h2 > a").attr("href"));
		});
	}

	public String getFirstMathResult() {
		if (links.isEmpty()) {
			scraping();
			if (links.isEmpty()) {
				throw new WebScrapingException("No result found");
			}
		}
		if (site != null && !links.get(0).contains(site.getUrl()))
			throw new WebScrapingException("No result in (" + site.getUrl() + ") found");
		else
			return links.get(0);
	}

	public List<String> getAllResult() {
		if (links.isEmpty()) {
			scraping();
			if (links.isEmpty()) {
				throw new WebScrapingException("No result found");
			}
		}
		return links;
	}
}
