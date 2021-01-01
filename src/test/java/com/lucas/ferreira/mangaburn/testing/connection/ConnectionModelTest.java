package com.lucas.ferreira.mangaburn.testing.connection;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class ConnectionModelTest {

	@Test
	public void connectUseEngine() {
		try {
		URL uc = new URL("https://www.google.com/search?q=test");

		HttpURLConnection httpConn = (HttpURLConnection) uc.openConnection();

		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
			System.out.println(httpConn.getHeaderFields());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
