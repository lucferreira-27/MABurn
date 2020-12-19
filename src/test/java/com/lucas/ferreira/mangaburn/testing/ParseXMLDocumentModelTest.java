package com.lucas.ferreira.mangaburn.testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.ParseXMLDocument;

public class ParseXMLDocumentModelTest {
	private ParseXMLDocument parseXml;
	private MangaDownloaded mangaExpected;
	private CollectionDatasReader reader;
	@Before
	public void setUp() {
		 reader = new CollectionDatasReader();
		parseXml = new ParseXMLDocument();
		mangaExpected = new MangaDownloaded();
		mangaExpected.setDestination("D:\\MangaBurnTest\\Dragon Ball");
		mangaExpected.setName("Dragon Ball");
		mangaExpected.setDataBaseUrl("https://myanimelist.net/manga/42/Dragon_Ball");
		mangaExpected.setLink("https://mangahost2.com/manga/dragon-ball-mh23440");
		mangaExpected.setHospedSite("mangahost");
		mangaExpected.setImageUrl("image");
		mangaExpected.setId(2);

	}
	@Test
	public void tranformContentToXMLTest() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        StringWriter writer = new StringWriter();
		try {
			transformer = transformerFactory.newTransformer();
		
	        DOMSource source = 	parseXml.tranformContentToXML(reader.getDocumentCollectionDates(),CollectionDatasReader.DATE_LOCAL);
	        StreamResult consoleResult = new StreamResult(writer);
	        transformer.transform(source, consoleResult);
	        assertNotEquals(writer.toString().length(), 0);
	        
	        
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
