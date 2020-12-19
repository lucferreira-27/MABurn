package com.lucas.ferreira.maburn.util;

public class StringUtil {
	public static String stringUtilFile(String str) {
		return str.replaceAll("[^0-9a-zA-Z/\\\\. -]+", "");
	}
}
