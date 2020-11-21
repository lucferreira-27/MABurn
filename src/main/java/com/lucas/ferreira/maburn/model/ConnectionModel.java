package com.lucas.ferreira.maburn.model;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;

public class ConnectionModel implements Callable<Response> {
	private String link;

	public ConnectionModel() {
		// TODO Auto-generated constructor stub
	}

	public ConnectionModel(String link) {
		// TODO Auto-generated constructor stub
		this.link = link;
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

	@Override
	public Response call() throws Exception {
		// TODO Auto-generated method stub
		return connect(link);
	}
}
