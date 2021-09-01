package com.lucas.ferreira.maburn.model.fetch;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class FetchSave {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
	private FetchRecover fetchRecover = new FetchRecover();
	private FetchReplacer fetchReplacer = new FetchReplacer();

	public void save(SaveData saveData) {
		if (existSite(saveData.getTitle(), saveData.getRegisteredSite())) {
			fetchReplacer.replace(saveData);
			return;
		}

		initSave(saveData);

	}

	private void initSave(SaveData saveData) {
		try {
			CollectionForm form = orchestrator.read();
			ListItemForm itemForm = form.getItems().stream().filter(item -> item.getId() == saveData.getTitle().getId())
					.findFirst().get();

			SiteForm siteForm = new SiteForm();

			siteForm.setItemLink(saveData.getUrl());

			siteForm.setSiteName(
					saveData.getRegisteredSite().getSiteConfig().getName().toUpperCase().replace(" ", "_"));
			itemForm.getScrapingLinks().add(siteForm);

			orchestrator.write(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean existSite(CollectionTitle title, RegisteredSite registeredSite) {
		String recoverFetch = fetchRecover.recover(title, registeredSite);
		return recoverFetch != null;

	}

	public void save(SiteForm siteForm, CollectionTitle title) {

		try {

			CollectionForm form = orchestrator.read();
			ListItemForm itemForm = form.getItems().stream().filter(item -> item.getId() == title.getId()).findFirst()
					.get();

			itemForm.getScrapingLinks().add(siteForm);

			orchestrator.write(form);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void save(ListItemForm siteForm, CollectionTitle title) {

		try {

			CollectionForm form = orchestrator.read();
			ListItemForm itemForm = form.getItems().stream().filter(item -> item.getId() == title.getId()).findFirst()
					.get();

			itemForm.setScrapingLinks(siteForm.getScrapingLinks());

			orchestrator.write(form);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
