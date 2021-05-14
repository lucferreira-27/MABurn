package com.lucas.ferreira.maburn.model.webscraping;

import java.util.List;

public interface SearchEngine {
	public String search(String query);
	public List<String> searchAll(String query);
}
