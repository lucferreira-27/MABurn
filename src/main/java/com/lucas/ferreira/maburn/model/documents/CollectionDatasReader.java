package com.lucas.ferreira.maburn.model.documents;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;

public class CollectionDatasReader {

	public final static String DATE_LOCAL = Documents.DOCUMENTS_LOCAL + "\\Documents\\ColletionsDates.xml";

	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private ParseXMLDocument parse;
	private DocumentBuilder builder;

	public CollectionDatasReader() {
		// TODO Auto-generated constructor stub
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new CollectionReaderException(e.getMessage());

		}
	}

	public Document getDocumentCollectionDates() {

		File collectionDates = getCollectionDateFile();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ParseXMLDocument parse;
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(collectionDates);

			return doc;
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CollectionReaderException(e.getMessage());

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CollectionReaderException(e.getMessage());

		}

	}

	public File getCollectionDateFile() {
		String local = DATE_LOCAL;
		File collectionDates = new File(local);
		if (!collectionDates.exists())
			throw new CollectionReaderException("ColletionsDates.xml not found in " + local);
		else
			return collectionDates;
	}

	public Document createCollectionDateDocument() {
		String local = DATE_LOCAL.substring(0, DATE_LOCAL.lastIndexOf("\\"));
		File fileLocal = new File(local);
		File file = new File(DATE_LOCAL);
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
