package com.lucas.ferreira.maburn.webserver;

import java.io.IOException;
import java.util.function.Consumer;

import com.sun.net.httpserver.HttpExchange;

public class InitHandle extends DefaultHandle implements Handle {

	public static final String PATH = "/init";
	private Consumer<String> onPostBody;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		String method = httpExchange.getRequestMethod();

		if (method.equals("GET")) {
			onGet(httpExchange);
		} else if (method.equals("POST")) {
			onPost(httpExchange);
		} else
			httpExchange.sendResponseHeaders(405, 0);

	}

	@Override
	public void onGet(HttpExchange httpExchange) throws IOException {

		defaultHeaders(httpExchange);
		httpExchange.sendResponseHeaders(200, 0);
		send(httpExchange, "APP INIT");

	}

	private void defaultHeaders(HttpExchange httpExchange) {

		httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
		httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

	}

	@Override
	public void onPost(HttpExchange httpExchange) throws IOException {
		defaultHeaders(httpExchange);
		httpExchange.sendResponseHeaders(200, 0);
		String body = responseBody(httpExchange.getRequestBody());
		onPostBody.accept(body);
		send(httpExchange, body);

	}

	public void onPostBody(Consumer<String> onPost) {	
		this.onPostBody = onPost;
	}


	@Override
	public String getPath() {
		return PATH;
	}





}
