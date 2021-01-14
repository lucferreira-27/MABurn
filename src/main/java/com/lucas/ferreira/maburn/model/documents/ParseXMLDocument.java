package com.lucas.ferreira.maburn.model.documents;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.lucas.ferreira.maburn.exceptions.ParseXMLDocumentException;

public class ParseXMLDocument {
	


	public DOMSource tranformContentToXML(Document doc, String local) {
		try {

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(local));

			transformer.transform(source, result);
			return source;
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			throw new ParseXMLDocumentException(e.getMessage());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ParseXMLDocumentException(e.getMessage());
		}

	}


}
