package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class XmlConfigurationOrchestrator {

	private final File FILE = new File(Documents.CONFIG_LOCAL);

	public ConfigForm read() throws JsonParseException, JsonMappingException, IOException {

		Reader reader = new Reader();

		return reader.readConfig(FILE);
	}
	
	
	public boolean write(ConfigForm form) {
		
		Writer writer = new Writer();
		
		return writer.writeConfigFromXml(form, FILE);
	}
}
