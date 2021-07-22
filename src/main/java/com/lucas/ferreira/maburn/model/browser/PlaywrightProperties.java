package com.lucas.ferreira.maburn.model.browser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lucas.ferreira.maburn.util.Resources;

public class PlaywrightProperties {
	public Map<String, String> load(String filename,String ... keys) throws IOException {
		Map<String, String> properties = new HashMap<>();
		Properties prop = getPropeties(filename);
		for(String key : keys) {
			String value = prop.getProperty(key);
			properties.put(key, value);
		}
		return properties;
	}

	private Properties getPropeties(String filename) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("playwright/" + filename);
		Properties prop = new Properties();

		if (inputStream != null) {
			prop.load(inputStream);
		}
		return prop;
	}
}
