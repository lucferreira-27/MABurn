package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class Updater {



	private Writer writer = new Writer();
	//private File file = new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Documesnts\\Test.xml");

	public void updateAnimeConfigFormDestination(ConfigForm configForm, String value, File file) {
		file = new File(Documents.CONFIG_LOCAL);
		configForm.getAnimeConfig().setCollectionDestination(value);

		writer.writeConfigFromXml(configForm, file);
	}

	public void updateItemFormByIdTitleDatabase(CollectionForm collectionForm, Integer id, String value, File file)
			throws JsonProcessingException {

		ListItemForm itemForm = getById(collectionForm, id);
		itemForm.setTitleDatabase(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdImageLocal(CollectionForm collectionForm, Integer id, String value, File file)
			throws JsonProcessingException {

		ListItemForm itemForm = getById(collectionForm, id);
		itemForm.setImageLocal(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdImageUrl(CollectionForm collectionForm, Integer id, String value, File file)
			throws JsonProcessingException {

		ListItemForm itemForm = getById(collectionForm, id);
		itemForm.setImageUrl(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdDestination(CollectionForm collectionForm, Integer id, String value, File file)
			throws JsonProcessingException {

		ListItemForm itemForm = getById(collectionForm, id);
		itemForm.setDestination(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	public void updateItemFormByIdCurretScrapingLink(CollectionForm collectionForm, Integer id, SiteForm value, File file)
			throws JsonProcessingException {

		ListItemForm itemForm = getById(collectionForm, id);
		itemForm.setCurretScrapingLink(value);
		writer.writeCollectionFormValueAsXml(collectionForm, file);

	}

	private ListItemForm getById(CollectionForm collectionForm, Integer id) {
		return collectionForm.getItems().stream().filter(item -> item.getId().equals(id)).findFirst().get();
	}
}
