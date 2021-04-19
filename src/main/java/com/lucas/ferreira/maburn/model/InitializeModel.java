package com.lucas.ferreira.maburn.model;

import javax.naming.ConfigurationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class InitializeModel {

	private XmlConfigurationOrchestrator configOrchestrator = new XmlConfigurationOrchestrator();
	private XmlCollectionOrchestrator collectionOrchestrator = new XmlCollectionOrchestrator();

	public void boot() {
		try {
			if (isFirstBoot()) {
				createDocumentesFolders();
			} else {
				initialize();
			}
		} catch (InitializeExcpetion e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public boolean isFirstBoot() {
		try {
			
			if(collectionOrchestrator.read() == null || configOrchestrator.read() == null) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		return false;
	}

	public void createDocumentesFolders() {
		CustomLogger.log("> CREATE DOCUMENTS FOLDERS");
		Documents.createDocumentFolders();
		try {
			collectionOrchestrator.write(new CollectionForm());
			configOrchestrator.write(new ConfigForm());

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() {
		CustomLogger.log("> WELCOME!");
		CustomLogger.log("CollectionData Local: " + Documents.DATA_LOCAL);
		CustomLogger.log("Configuration Local: " + Documents.CONFIG_LOCAL);

	}
}
