package com.lucas.ferreira.maburn.model.webscraping;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;

public class Scraper {
	public Elements scrapeSnippet(Document doc, String querry) {
		try {
			Elements elements = doc.select(querry);
			if(elements.size() == 0)
				throw new WebScrapingException("Size: 0");
			return doc.select(querry);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WebScrapingException("Element don't found in querry (" + e.getMessage() + ")");
		}
	}
	
	public Elements scrapeAjaxSnippet(Document doc, String querry) {
		try {
			Elements elements = doc.select(querry);
			if(elements.size() == 0)
				throw new WebScrapingException("Size: 0");
			return doc.select(querry);
		} catch (Exception e) {
			// TODO: handle exception
			throw new WebScrapingException("Element don't found in querry (" + e.getMessage() + ")");
		}
	}
}
