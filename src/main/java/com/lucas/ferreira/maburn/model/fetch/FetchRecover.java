package com.lucas.ferreira.maburn.model.fetch;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class FetchRecover {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public String recover(CollectionTitle title, RegisteredSite registeredSite) {
		try {
			ListItemForm form = orchestrator.readById(title.getId());
			LOGGER.config("Recover Links: " + form.getScrapingLinks());

			Optional<SiteForm> result = form.getScrapingLinks().stream().filter(link -> {

				return link.getSiteName()
						.equals(registeredSite.getSiteConfig().getName().toUpperCase().replace(" ", "_"));
			}).findFirst();

			if (result.isPresent()) {
				return result.get().getItemLink();
			} else {
				return null;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}

	public String recoverWithUrl(SaveData saveData) {
		try {
			ListItemForm form = orchestrator.readById(saveData.getTitle().getId());

			LOGGER.config("Recover Links: " + form.getScrapingLinks());

			Optional<SiteForm> result = form.getScrapingLinks().stream().filter(link -> {

				return link.getSiteName()
						.equals(saveData.getRegisteredSite().getSiteConfig().getName().toUpperCase().replace(" ", "_"))
						&& link.getItemLink().equals(saveData.getUrl());
			}).findFirst();

			if (result.isPresent()) {
				return result.get().getItemLink();
			} else {
				return null;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}

}
