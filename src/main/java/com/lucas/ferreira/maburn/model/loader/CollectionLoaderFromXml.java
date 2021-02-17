package com.lucas.ferreira.maburn.model.loader;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;

public class CollectionLoaderFromXml {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();

	public CollectionForm loadCollection() {

		try {
			return orchestrator.read();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ItemForm loadCollectionItem(Integer id) {

		try {
			return orchestrator.readById(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
