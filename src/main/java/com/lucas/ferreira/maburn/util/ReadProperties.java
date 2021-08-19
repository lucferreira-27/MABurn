package com.lucas.ferreira.maburn.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadProperties {
	public Map<String, String> load(String filename, String... keys) throws IOException {
		Map<String, String> properties = new HashMap<>();
		Properties prop = getPropeties(filename);
		for (String key : keys) {
			String value = prop.getProperty(key);

			properties.put(key, value);
		}
		return properties;
	}
	public Properties load(InputStream inputStream) throws IOException {
		Properties prop = getPropeties(inputStream);

		
		return prop;
	}
	public void update(String filename, String key, String value) throws IOException {
		Properties properties = getPropeties(filename);
		properties.setProperty(key, value);
		save(filename, properties);
	}

	private void save(String filename, Properties properties) throws FileNotFoundException, IOException {
		properties.store(new FileOutputStream(filename), null);

	}

	private Properties getPropeties(String filename) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(filename);
		Properties prop = new Properties();

		if (inputStream != null) {
			prop.load(inputStream);
		}
		return prop;
	}
	private Properties getPropeties(InputStream in) throws IOException {
		Properties prop = new Properties();

		if (in != null) {
			prop.load(in);
		}
		return prop;
	}
}
