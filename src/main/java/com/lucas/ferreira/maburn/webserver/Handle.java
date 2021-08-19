package com.lucas.ferreira.maburn.webserver;

import java.io.IOException;
import java.util.function.Consumer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public interface Handle extends HttpHandler {

	public void onGet(HttpExchange httpExchange) throws IOException;

	public void onPost(HttpExchange httpExchange) throws IOException;
	
	public void onPostBody(Consumer<String> onPost);
	public String getPath();
}
