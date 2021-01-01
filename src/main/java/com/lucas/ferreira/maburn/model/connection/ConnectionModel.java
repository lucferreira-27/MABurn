package com.lucas.ferreira.maburn.model.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.lucas.ferreira.maburn.exceptions.ConnectionException;

public class ConnectionModel implements Callable<String> {
	private String link;
	private static ConnectionConfig config;

	public ConnectionModel(ConnectionConfig config) {
		// TODO Auto-generated constructor stub
		this.config = config;
	}

	public String connectAndReturnLocation(String url) {

		return null;
	}

	public ConnectionModel(String link) {
		// TODO Auto-generated constructor stub
		this.link = link;
	}



	public static String connect(String url, int attempts) {
		int attempt = 0;

		while (true) {
			try {
				return connect(url);
			} catch (ConnectionException e) {
				// TODO: handle exception
				attempt++;
				if (attempt >= attempts) {
					throw new ConnectionException(e.getMessage());
				}
			}
		}

	}

	public static String connect(String url) throws ConnectionException {
		boolean retry = false;
		HttpURLConnection httpConn = null;

		try {
			url = url.replaceAll(" ", "%20");

			httpConn = Httpsetup(url);

			return inputStreamToString(httpConn);
		} catch (IOException e) {
			// TODO: handle exception
			int responseCode;
			try {
				responseCode = httpConn.getResponseCode();
				throw new ConnectionException(String.valueOf(responseCode));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				throw new ConnectionException(e.getMessage());

			}

		} 
	}

	private static String inputStreamToString(HttpURLConnection httpConn) throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader((httpConn.getInputStream())));
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}

		return sb.toString();

	}

	private static HttpURLConnection Httpsetup(String url) throws IOException {
		URL uc = new URL(url);

		HttpURLConnection httpConn = (HttpURLConnection) uc.openConnection();

		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");

		return httpConn;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		return connect(link);
	}
}
