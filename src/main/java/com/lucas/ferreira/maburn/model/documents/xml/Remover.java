package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;

import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;

public class Remover {
	public boolean removeItemFormById(CollectionForm form, Integer id) throws XMLStreamException, JsonProcessingException {
		
		
		form.getItems().forEach(f ->{
			System.out.println(f.getId().equals(id));
		});
		
		boolean remove = form.getItems().removeIf(item -> item.getId().equals(id));
		
		Writer writer = new Writer();
		writer.writeCollectionFormValueAsXml(form, new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Documents\\Test.xml"));
		
		return remove;
	}

}
