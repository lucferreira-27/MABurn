package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class Reader {
	
	public ConfigForm readConfig(File file) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new XmlMapper();
		ConfigForm form = objectMapper.readValue(file, ConfigForm.class);
		return form;
	}
	
	public CollectionForm readCollectionForm(File file) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new XmlMapper();
		CollectionForm form = objectMapper.readValue(file, CollectionForm.class);
		return form;
	}

	public ItemForm readItemFormById(File file, Integer id)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new XmlMapper();
		CollectionForm collectionForm = objectMapper.readValue(file, CollectionForm.class);

		Optional<ItemForm> optionalItem = collectionForm.getItems().stream().filter(form -> form.getId().equals(id))
				.findAny();
		if (optionalItem.isPresent()) {
			return optionalItem.get();
		}
		return null;

	}
}
