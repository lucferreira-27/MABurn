package com.lucas.ferreira.maburn.model;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;

public class ConnectionModel {
	public ConnectionModel() {
		// TODO Auto-generated constructor stub
	}

	public static Response connect(String url) throws ConnectionException {

		try {
			return Jsoup.connect(url).ignoreContentType(true).execute();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConnectionException(e.getMessage());
		}
	}

	public static Document connect(String url, int ms) throws ConnectionException {
		try {
			return Jsoup.connect(url).timeout(ms).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConnectionException(e.getMessage());

		}
	}
}
