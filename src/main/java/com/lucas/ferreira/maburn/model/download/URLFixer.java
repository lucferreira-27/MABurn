package com.lucas.ferreira.maburn.model.download;

public class URLFixer {
	public static boolean needBeFixed(String url) {
		boolean check = !url.contains("https");
		return check;
	}
	public static String addHttpInUrl(String url) {
		 url = "https:" + url;
		return url;
	}
}
