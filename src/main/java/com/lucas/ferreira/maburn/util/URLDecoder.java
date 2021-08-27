package com.lucas.ferreira.maburn.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class URLDecoder {
	public static String decode(String url) {
		 try {
			return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
}
