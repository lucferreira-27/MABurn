package com.lucas.ferreira.maburn.model.fetch;

import java.io.IOException;
import java.util.Optional;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class FetchReplacer {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();

	public void replace(SaveData saveData) {
		try {
			System.out.println("!!");
			ListItemForm form = orchestrator.readById(saveData.getTitle().getId());
			
			System.out.println(form.getScrapingLinks());

			Optional<SiteForm> result = form.getScrapingLinks().stream().filter(link -> {

				return link.getSiteName()
						.equals(saveData.getRegisteredSite().getSiteConfig().getName().toUpperCase().replace(" ", "_"));
			}).findFirst();

			if (result.isPresent()) {
				SiteForm oldForm =  result.get();
				SiteForm siteForm = new SiteForm();
				siteForm.setItemLink(saveData.getUrl());
				siteForm.setSiteName(oldForm.getSiteName())
				;
				form.getScrapingLinks().remove(oldForm);
				form.getScrapingLinks().add(siteForm);
				FetchSave fetchSave = new FetchSave();
				fetchSave.save(form,saveData.getTitle());

			}
		} catch (IOException e) {

			e.printStackTrace();
		}


	}
}
