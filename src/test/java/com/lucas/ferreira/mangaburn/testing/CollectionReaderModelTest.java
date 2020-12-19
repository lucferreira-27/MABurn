package com.lucas.ferreira.mangaburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;

public class CollectionReaderModelTest {
	private CollectionDatasReader collectionReader;
	@Before
	public void setUp() {
		 collectionReader = new CollectionDatasReader();
	}
	@Test
	public void getSettingsFile() {
		File settingFile = collectionReader.getCollectionDateFile();
		assertTrue(settingFile.exists());
	}
	@Test
	public void readerCollectionsDatesTest() {
		Document doc =  collectionReader.getDocumentCollectionDates();
	}
	
}
