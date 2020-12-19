package com.lucas.ferreira.maburn.model.documents;

import java.io.File;
import java.io.IOException;

import javax.naming.ConfigurationException;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;

public class ConfigurationReader {

	public final static String CONFIG_LOCAL = Documents.DOCUMENTS_LOCAL + "\\Documents\\Config.xml";

	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private ParseXMLDocument parse;
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
		String local = CONFIG_LOCAL;
		File config = new File(local);
		if (!config.exists())
			throw new ConfigurationException("Config.xml not found in " + local);
		else
			return config;
	}
	public Document createConfigurationDocument() {
		String local = CONFIG_LOCAL.substring(0,CONFIG_LOCAL.lastIndexOf("\\"));
		File fileLocal = new File(local);
		File file = new File(CONFIG_LOCAL);
		try {
			fileLocal.mkdirs();
			file.createNewFile();
			Document document = builder.newDocument();
			return document;
		} catch (IOException e) {

			throw new CollectionReaderException("ColletionsDates.xml can't be created " + local);

		}
	}
	
}
