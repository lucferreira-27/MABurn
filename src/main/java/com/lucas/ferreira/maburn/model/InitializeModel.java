package com.lucas.ferreira.maburn.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.exceptions.InitializeExcpetion;
import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.sites.FindResourcesSites;
import com.lucas.ferreira.maburn.util.ResourcesFile;
import com.lucas.ferreira.maburn.util.Time;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

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

	private boolean isFirstBoot() {
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

	private void createDocumentesFolders() {
		LOGGER.info("CREATE DOCUMENTS FOLDERS");
		Documents.createDocumentFolders();



		try {

			collectionOrchestrator.write(new CollectionForm());
			configOrchestrator.write(new ConfigForm());

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
	}

	private void initialize() throws InitializeExcpetion {
		try {
			initializeLocalServer();
			initializePlaywright();
			initializeLogsFile();
			initializeScrapingScripts();
			welcomeMessage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new InitializeExcpetion(e.getMessage());
		}

	}

	private  void initializeLogsFile(){
		try{
			System.out.println(Time.instant());
			String filename = "LOGS_" + Time.instant() + ".log";
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(new FileHandler(Documents.LOGS_LOCAL + "\\" + filename, true));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	private void initializeScrapingScripts() {
		FindResourcesSites findResourcesSites = new FindResourcesSites();
		try {
			Path pathResourcesScript = findResourcesSites.findAll();
			Path pathLocalScript = Paths.get(Documents.SCRIPT_LOCAL);
			if(Files.list(pathResourcesScript).count() > Files.list(pathLocalScript).count()){
				LOGGER.info("Extracting scripts from resources to Scripts folder");
				ResourcesFile.listFolders(pathResourcesScript, 0).forEach(p -> {
					try {
						ResourcesFile.copyDirectory(p, pathLocalScript);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initializePlaywright() throws IOException {
		PlaywrightSettings.initConfig();

	}
	private void initializeLocalServer() throws IOException {
		LocalServer localServer = new LocalServer();
		WebServer webServer = localServer.create();
		webServer.start();
	}

	private void welcomeMessage() {
		LOGGER.fine("> WELCOME!");
		LOGGER.info("CollectionData Local: " + Documents.DATA_LOCAL);
		LOGGER.info("Configuration Local: " + Documents.CONFIG_LOCAL);
	}
}
