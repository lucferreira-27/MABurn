package com.lucas.ferreira.maburn.model.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.comparator.SearchResultComparator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchController {
	private BooleanProperty searchFailed = new SimpleBooleanProperty();
	private ObjectProperty<SearchResult> searchResult = new SimpleObjectProperty<SearchResult>();
	private StringProperty querryProperty = new SimpleStringProperty();

	private CollectionItem item;
	private WebScraping scraping;

	public SearchController(CollectionItem item) {
		// TODO Auto-generated constructor stub
		this.scraping = item.getWebScraping();
		this.item = item;
		
		querryProperty.addListener((obs, oldvalue, newvalue) ->{
			search(newvalue);
		});
	}

	public void search(String querry) {
		ArrayList<String> queries = new ArrayList<String>();

		try {
			queries.add(querry);
			List<SearchResult> searchTitles = scraping.fetchSearchTitle(querry);
			if (searchTitles == null || searchTitles.isEmpty()) {
				for (Entry<String, String> title : item.getTitles().entrySet()) {
					if (title.getValue().equalsIgnoreCase(item.getTitleDataBase()) || title.getValue().isEmpty())
						continue;

					queries.add(title.getValue());
					searchTitles = scraping.fetchSearchTitle(title.getValue());

					if (searchTitles == null || searchTitles.isEmpty()) {
						continue;
					} else {
						break;
					}
				}

				if (searchTitles == null || searchTitles.isEmpty()) {
					searchResult.set(null);
					searchFailed.set(true);
					return;
				}
			}

			searchTitles.sort(new SearchResultComparator(querry));
			searchResult.set(searchTitles.get(0));
			searchFailed.set(false);
		
		} catch (WebScrapingException e) {
			// TODO: handle exception
			searchFailed.set(true);
		}

	}

	public boolean getSearchFailed() {
		return searchFailed.get();
	}

	public SearchResult getSearchResult() {
		return searchResult.get();
	}

	public ObjectProperty<SearchResult> searchResultProperty() {
		return searchResult;
	}

	public BooleanProperty searchFailedtProperty() {
		return searchFailed;
	}

}
