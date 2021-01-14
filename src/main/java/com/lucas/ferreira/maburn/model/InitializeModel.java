package com.lucas.ferreira.maburn.model;

import javax.naming.ConfigurationException;

import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class InitializeModel {
	private ConfigurationReader config = new ConfigurationReader();
	private CollectionDatasReader datesReader = new CollectionDatasReader();



	public void boot() {
		try {
			if (isFirstBoot()) {
				createDocumentesFolders();
				createDocumentFiles();
				initializeConfiguration();
			} else {
				initialize();
			}
		} catch (InitializeExcpetion e) {
			// TODO: handle exception
			createDocumentFiles();
		}

	}

	public boolean isFirstBoot() {
		try {
			config.getSettingsFile();
			datesReader.getCollectionDateFile();
		} catch (CollectionReaderException | ConfigurationException e) {
			// TODO: handle exception
			return true;
		}
		return false;
	}

	public void createDocumentesFolders() {
		CustomLogger.log("> CREATE DOCUMENTS FOLDERS");
		Documents.createDocumentFolders();
	}

	public void createDocumentFiles() {
		DocumentConfiguration.createDocument();
		DocumentCollectionReader.createDocument();
	}

	public void initializeConfiguration() {

		
		new DocumentConfiguration(config.getDocumentConfiguration());


	}

	public void initialize() {
		CustomLogger.log("> WELCOME!");
	}
}
