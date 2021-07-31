package com.lucas.ferreira.maburn.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.browser.PlaywrightProperties;
import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
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
				initialize();
			} else {
				initialize();
			}
		} catch (InitializeExcpetion e) {

			e.printStackTrace();
			System.out.println("BOOT ERROR CLOSING ....");
			System.exit(0);
		}

	}

	public boolean isFirstBoot() {
		try {

			if (collectionOrchestrator.read() == null || configOrchestrator.read() == null) {
				return true;
			}
		} catch (Exception e) {

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

			e.printStackTrace();
		}
	}

	public void initialize() throws InitializeExcpetion {
		try {
			initializePlaywright();
			welcomeMessage();
		} catch (Exception e) {
			// TODO: handle exception
			throw new InitializeExcpetion(e.getMessage());
		}

	}

	public void initializePlaywright() throws IOException {
		PlaywrightSettings.initConfig();

	}

	public void welcomeMessage() {
		CustomLogger.log("> WELCOME!");
		CustomLogger.log("CollectionData Local: " + Documents.DATA_LOCAL);
		CustomLogger.log("Configuration Local: " + Documents.CONFIG_LOCAL);
	}
}
