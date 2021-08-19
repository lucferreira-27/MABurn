package com.lucas.ferreira.maburn.model.browser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lucas.ferreira.maburn.util.ReadProperties;
import com.lucas.ferreira.maburn.util.Resources;

public class PlaywrightProperties {

	ReadProperties readProperties = new ReadProperties();

	public Map<String, String> load(String filename,String ... keys) throws IOException {

		return readProperties.load(filename, keys);
	}
	
	public void update(String filename, String key, String value) throws IOException {
		readProperties.update(filename, key, value);
	}

	

}
