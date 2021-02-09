package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;

public class Updater {

	private Writer writer = new Writer();
	private static File file = new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Documents\\Test.xml");

	public void updateItemFormByIdTitleDatabase(CollectionForm collectionForm, Integer id, String value)
			throws JsonProcessingException {

		ItemForm itemForm = getById(collectionForm, id);
		itemForm.setTitleDatabase(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdImageLocal(CollectionForm collectionForm, Integer id, String value)
			throws JsonProcessingException {

		ItemForm itemForm = getById(collectionForm, id);
		itemForm.setImageLocal(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdImageUrl(CollectionForm collectionForm, Integer id, String value)
			throws JsonProcessingException {

		ItemForm itemForm = getById(collectionForm, id);
		itemForm.setImageUrl(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdDestination(CollectionForm collectionForm, Integer id, String value)
			throws JsonProcessingException {

		ItemForm itemForm = getById(collectionForm, id);
		itemForm.setDestination(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdCurretScrapingLink(CollectionForm collectionForm, Integer id, String value)
			throws JsonProcessingException {

		ItemForm itemForm = getById(collectionForm, id);
		itemForm.setCurretScrapingLink(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	private ItemForm getById(CollectionForm collectionForm, Integer id) {
		return collectionForm.getItems().stream().filter(item -> item.getId().equals(id)).findFirst().get();
	}
}
