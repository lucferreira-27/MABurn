package com.lucas.ferreira.maburn.testing.xml;

import java.io.File;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.documents.xml.Reader;
import com.lucas.ferreira.maburn.model.documents.xml.Remover;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;

public class RemoverTest {
	
	@Test
	public void removeItemFormByIdTest() {
		try {
		Reader reader = new Reader();
		CollectionForm collectionForm = reader.readCollectionForm(new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Documents\\Test.xml"));
		
		Remover remover = new Remover();
	     
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
