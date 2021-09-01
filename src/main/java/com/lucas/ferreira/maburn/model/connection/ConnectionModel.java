package com.lucas.ferreira.maburn.model.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;

public class ConnectionModel implements Callable<String> {
	private String link;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public ConnectionModel(String link) {
		this.link = link;
	}

	public static String connect(String url, int attempts) {
		int attempt = 0;

		while (true) {
			try {
				return connect(url);
			} catch (Exception e) {
				
				attempt++;
				if (attempt >= attempts) {
					throw new ConnectionException(e.getMessage());
				}
			}
		}

	}

	public static HttpURLConnection openConnection(String url, int attempts) {
		int attempt = 0;

		while (true) {
			try {
				return Httpsetup(url);

			} catch (Exception e) {
				
				attempt++;
				if (attempt >= attempts) {
					throw new ConnectionException(e.getMessage());
				}
			}
		}

	}

	public static InputStream getInputStream(HttpURLConnection httpURLConnection, int attempts) {
		int attempt = 0;
		InputStream is;
		while (true) {
			try {
				is = httpURLConnection.getInputStream();
				return is;

			} catch (Exception e) {
				
				attempt++;
				if (attempt >= attempts) {
					throw new ConnectionException(e.getMessage());
				}
			}
		}

	}

	public static String connect(String url) throws ConnectionException{
		
		
		HttpURLConnection httpConn = null;
		
		try {
			

			httpConn = Httpsetup(url);
			return inputStreamToString(httpConn);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			int responseCode;
			try {
				responseCode = httpConn.getResponseCode();
				throw new ConnectionException(String.valueOf(responseCode));

			} catch (IOException e1) {
				 
				throw new ConnectionException(e.getMessage());

			}

		}
	}

	public static String inputStreamToString(HttpURLConnection httpConn) throws IOException {
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
		
		return connect(link);
	}
}
