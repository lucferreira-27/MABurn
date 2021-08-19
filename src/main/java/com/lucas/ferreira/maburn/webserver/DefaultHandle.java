package com.lucas.ferreira.maburn.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class DefaultHandle {
	protected void send(HttpExchange httpExchange, String content) throws IOException {
		OutputStream os = httpExchange.getResponseBody();
		os.write(content.getBytes());
		os.close();
	}

	protected String responseBody(InputStream inputStream) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		int b;
		StringBuilder buf = new StringBuilder(512);
		while ((b = br.read()) != -1) {
			buf.append((char) b);
		}
		br.close();
		isr.close();
		return buf.toString();

	}
	
}
