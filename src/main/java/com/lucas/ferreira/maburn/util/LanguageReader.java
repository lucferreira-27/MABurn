package com.lucas.ferreira.maburn.util;

import java.io.IOException;

public class LanguageReader {
	public static String read(String value) {
		try {
			return new LanguagesProperties("en_us").getPropValue(value);
		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}
}
