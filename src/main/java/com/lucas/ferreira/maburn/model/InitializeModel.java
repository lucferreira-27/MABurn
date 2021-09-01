package com.lucas.ferreira.maburn.model;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;

public class InitializeModel {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
			LOGGER.severe("BOOT ERROR CLOSING ...." + e.getMessage());
			System.exit(0);
		}

	}

	public boolean isFirstBoot() {
		try {

			if (collectionOrchestrator.read() == null || configOrchestrator.read() == null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	public void createDocumentesFolders() {
		LOGGER.info("> CREATE DOCUMENTS FOLDERS");
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
			initializeLocalServer();
			initializePlaywright();
			welcomeMessage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new InitializeExcpetion(e.getMessage());
		}

	}

	public void initializePlaywright() throws IOException {
		PlaywrightSettings.initConfig();

	}
	public void initializeLocalServer() throws IOException {
		LocalServer localServer = new LocalServer();
		WebServer webServer = localServer.create();
		webServer.start();
	}

	public void welcomeMessage() {
		LOGGER.fine("> WELCOME!");
		LOGGER.info("CollectionData Local: " + Documents.DATA_LOCAL);
		LOGGER.info("Configuration Local: " + Documents.CONFIG_LOCAL);
	}
}
