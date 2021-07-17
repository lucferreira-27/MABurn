package com.lucas.ferreira.maburn.model.fetch;

import java.io.IOException;
import java.util.Optional;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FetchRecover {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();

	
	
	public String recover(CollectionTitle title, Sites site) {
		try {
			ListItemForm form = orchestrator.readById(title.getId());
			Optional<SiteForm> result = form.getScrapingLinks().stream().filter(link -> link.getSiteName() == site).findFirst();
			if(result.isPresent()) {
				return result.get().getItemLink();
			}else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
