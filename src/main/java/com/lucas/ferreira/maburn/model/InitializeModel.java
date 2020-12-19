package com.lucas.ferreira.maburn.model;

import javax.naming.ConfigurationException;

import com.lucas.ferreira.maburn.exceptions.CollectionReaderException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.view.HelperInterfaceView;
import com.lucas.ferreira.maburn.view.View;

public class InitializeModel {
	private ConfigurationReader config = new ConfigurationReader();
	private CollectionDatasReader datesReader = new CollectionDatasReader();
	private DocumentCollectionReader docCollectionReader;
	private DocumentConfiguration docConfiguration;
	private HelperInterfaceView helperView;
	private View view;

	public InitializeModel(HelperInterfaceView helperView) {
		// TODO Auto-generated constructor stub
		this.helperView = helperView;
	}

	public InitializeModel(View view) {
		// TODO Auto-generated constructor stub

		this.view = view;
	}

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
		System.out.println("> CREATE DOCUMENTS FOLDERS");
		Documents.createDocumentFolders();
	}

	public void createDocumentFiles() {
		DocumentConfiguration.createDocument();
		DocumentCollectionReader.createDocument();
	}

	public void initializeConfiguration() {

		
		docConfiguration = new DocumentConfiguration(config.getDocumentConfiguration());


	}

	public void initialize() {
		System.out.println("> WELCOME!");
	}
}
