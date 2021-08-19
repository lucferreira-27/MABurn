package com.lucas.ferreira.maburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;

public class LocalServerTest {
	private LocalServer localServer = new LocalServer();

	@BeforeEach
	public void createServer() throws IOException {
		if (LocalServer.getWebServer() == null)
			localServer.create();
		else if (!LocalServer.getWebServer().isOpen()) {
			localServer.create();
		}

	}

	public void stopServer() {

		LocalServer.getWebServer().stop();
	}

	@Test
	public void createServerTestShouldThrowException() throws IOException {
		Assertions.assertThrows(IllegalAccessError.class, () -> {
			LocalServer localServer = new LocalServer();
			WebServer server = localServer.create();
		});

	}

	@Test
	public void startServerTest() throws IOException {
		
		WebServer webServer = LocalServer.getWebServer();
		webServer.start();
		boolean isOpen = webServer.isOpen();
		assertTrue(isOpen);

	}

	@Test
	public void stopServerTest() throws IOException {

		WebServer webServer = LocalServer.getWebServer();
		webServer.start();
		webServer.stop();
		boolean isOpen = webServer.isOpen();
		assertTrue(isOpen != true);
	}

	@Test
	public void onGetInit() throws IOException {
		WebServer webServer = LocalServer.getWebServer();
		webServer.start();

		String url = "http://localhost:" + LocalServer.PORT + "/init";
		URL conn = new URL(url);
		HttpURLConnection httpConnection = (HttpURLConnection) conn.openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.connect();

		String expect = "APP INIT";
		String body = ConnectionModel.inputStreamToString(httpConnection);
		assertThat(body, is(equalTo(expect)));

	}

	@Test
	public void onGetEnd() throws IOException {

		WebServer webServer = LocalServer.getWebServer();
		webServer.start();

		String url = "http://localhost:" + LocalServer.PORT + "/end";
		URL conn = new URL(url);
		HttpURLConnection httpConnection = (HttpURLConnection) conn.openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.connect();

		String expect = "APP END";
		String body = ConnectionModel.inputStreamToString(httpConnection);
		assertThat(body, is(equalTo(expect)));
	}

	@Test
	public void onPostEnd() throws IOException {

		WebServer webServer = LocalServer.getWebServer();
		webServer.start();

		String url = "http://localhost:" + LocalServer.PORT + "/end";
		URL conn = new URL(url);
		HttpURLConnection httpConnection = (HttpURLConnection) conn.openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/json; utf-8");
		httpConnection.setRequestProperty("Accept", "application/json");
		httpConnection.setDoOutput(true);
		String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
		try (OutputStream os = httpConnection.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		httpConnection.connect();
		String expect = jsonInputString;
		String body = ConnectionModel.inputStreamToString(httpConnection);
		assertThat(body, is(equalTo(expect)));
		stopServer();
	}
}
