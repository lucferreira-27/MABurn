package com.lucas.ferreira.maburn.fetch;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FetchSave {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
	private FetchRecover fetchRecover = new FetchRecover();

	public void save(CollectionTitle title, Sites site, String url) {

		String recoverFetch = fetchRecover.recover(title, site);
		if (recoverFetch != null)
			try {

				CollectionForm form = orchestrator.read();
				ListItemForm itemForm = form.getItems().stream().filter(item -> item.getId() == title.getId())
						.findFirst().get();

				SiteForm siteForm = new SiteForm();

				siteForm.setItemLink(url);

				siteForm.setSiteName(site);
				itemForm.getScrapingLinks().add(siteForm);

				orchestrator.write(form);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
