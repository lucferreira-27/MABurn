package com.lucas.ferreira.maburn.model.search;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;

public interface SearchEngine {
	public String search() throws WebScrapingException, ConnectionException;
}
