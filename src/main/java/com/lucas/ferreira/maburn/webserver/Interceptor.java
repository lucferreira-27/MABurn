package com.lucas.ferreira.maburn.webserver;

import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.Headers;

public class Interceptor {
	
	private static List<Headers> listHeaders = new ArrayList<>();

	public void onIntercept(Headers headers) {
		listHeaders.add(headers);
	}

	public List<Headers> getListHeaders() {
		return listHeaders;
	}
	public static void clear() {
		listHeaders.clear();
	}
}
