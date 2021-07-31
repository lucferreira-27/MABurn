package com.lucas.ferreira.mangaburn.testing.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.documents.xml.Reader;

public class ReaderTest {

	@Test
	public void readCollectionFormTest() {
		Reader reader = new Reader();

		try {
			reader.readCollectionForm(
					new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Documents\\Test.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
