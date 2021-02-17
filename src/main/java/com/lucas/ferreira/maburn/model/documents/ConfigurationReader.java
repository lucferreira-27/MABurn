package com.lucas.ferreira.maburn.model.documents;

import java.io.File;
import java.io.IOException;

import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;

public class ConfigurationReader {


	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder;
	public ConfigurationReader() {
		// TODO Auto-generated constructor stub
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new CollectionReaderException(e.getMessage());

		}
	}
	public Document getDocumentConfiguration() {
		try {
		File collectionDates = getSettingsFile();

		

			Document doc = builder.parse(collectionDates);

			return doc;
		} catch (SAXException | IOException | ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CollectionReaderException(e.getMessage());

		}

	}
	
	public File getSettingsFile() throws ConfigurationException {
		String local = Documents.CONFIG_LOCAL;
		File config = new File(local);
		if (!config.exists())
			throw new ConfigurationException("Config.xml not found in " + local);
		else
			return config;
	}
	public Document createConfigurationDocument() {
		String local = Documents.CONFIG_LOCAL.substring(0,Documents.CONFIG_LOCAL.lastIndexOf("\\"));
		File fileLocal = new File(local);
		File file = new File(Documents.CONFIG_LOCAL);
		try {
			System.out.println(file.getAbsolutePath());
			fileLocal.mkdirs();
			file.createNewFile();
			Document document = builder.newDocument();
			return document;
		} catch (IOException e) {
			e.printStackTrace();
			throw new CollectionReaderException("ColletionsDates.xml can't be created " + local);

		}
	}
	
}
