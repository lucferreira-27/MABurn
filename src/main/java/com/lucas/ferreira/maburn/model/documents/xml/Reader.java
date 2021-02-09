package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;

public class Reader {


	public CollectionForm readCollectionForm(File file) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new XmlMapper();
		CollectionForm form = objectMapper.readValue(file, CollectionForm.class);
		return form;
	}
}
