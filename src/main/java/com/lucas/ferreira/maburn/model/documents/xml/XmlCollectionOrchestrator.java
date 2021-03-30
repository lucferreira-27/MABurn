package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;

public class XmlCollectionOrchestrator {

	private final File FILE = new File(Documents.DATA_LOCAL);

	public XmlCollectionOrchestrator() {
		// TODO Auto-generated constructor stub
	}

	public boolean removeById(CollectionForm collectionForm, Integer id) throws JsonProcessingException {
		Remover remover = new Remover();

		return remover.removeItemFormByIdAndWrite(collectionForm, id);
	}

	public boolean create() throws JsonProcessingException {
		Writer writer = new Writer();
		writer.writeNewCollectionFormValueAsXml(FILE);
		return false;
	}

	public boolean write(CollectionForm form) throws JsonProcessingException {
		Writer writer = new Writer();
		if (!FILE.exists()) {
			create();
		}
		System.out.println(FILE);
		writer.writeCollectionFormValueAsXml(form, FILE);
		return false;
	}

	public boolean updateById(CollectionForm collectionForm, Integer id, UpdateType type, Object... value)
			throws Exception {

		Updater updater = new Updater();
		try {
			switch (type) {
			case TITLE_DATABASE:
				updater.updateItemFormByIdTitleDatabase(collectionForm, id, (String) value[0], FILE);

				break;
			case DESTINATION:
				updater.updateItemFormByIdDestination(collectionForm, id, (String) value[0], FILE);

				break;
			case IMAGE_LOCAL:
				updater.updateItemFormByIdImageLocal(collectionForm, id, (String) value[0], FILE);
				break;
			case IMAGE_URL:
				updater.updateItemFormByIdImageUrl(collectionForm, id, (String) value[0], FILE);
				break;
			case CURRENT_SCRAPING_LINK:
				updater.updateItemFormByIdCurretScrapingLink(collectionForm, id, (String) value[0], FILE);

				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return false;
	}

	public CollectionForm read() throws JsonParseException, JsonMappingException, IOException {

		Reader reader = new Reader();

		return reader.readCollectionForm(FILE);
	}

	public ListItemForm readById(Integer id) throws JsonParseException, JsonMappingException, IOException {

		Reader reader = new Reader();

		return reader.readItemFormById(FILE, id);
	}
}
