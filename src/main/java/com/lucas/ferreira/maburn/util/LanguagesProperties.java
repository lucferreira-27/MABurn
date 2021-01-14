package com.lucas.ferreira.maburn.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LanguagesProperties {


	private InputStream inputStream;

	public LanguagesProperties(String proprie) {
		// TODO Auto-generated constructor stub

		inputStream = Resources.getResourceAsStream("languages/" + proprie + ".properties");
	}

	public String getPropValue(String value) throws IOException {
		Properties prop = new Properties();
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file  not found in the classpath");
		}
		
		return prop.getProperty(value);
	}

}
