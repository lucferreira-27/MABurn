package com.lucas.ferreira.maburn.model.search;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Databases;

public class Result {
	private String title;
	private Category category;
	private String synopsis;
	private Map<String, String> posterImageLink = new LinkedHashMap<>();
	private Databases database;

	public Result(Databases database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getPosterImageLink(String key) {
		return posterImageLink.get(key);
	}

	public void addPosterImageLink(String key, String value) {
		this.posterImageLink.put(key, value);
	}

	public Databases getDatabase() {
		return database;
	}

	public void setDatabase(Databases database) {
		this.database = database;
	}

}
